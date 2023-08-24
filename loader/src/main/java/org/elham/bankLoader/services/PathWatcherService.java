package org.elham.bankLoader.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elham.bankLoader.model.PropertyContainer;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;

@Component
public class PathWatcherService {
    private final PropertyContainer propertyContainer;
    private final AccountService accountService;
    private final CustomerService customerService;

    private final TransactionService transactionService;
    private final ConnectAccountsToCustomersService connectAccountsToCustomersService;
    private final FileProcessorService fileProcessor;

    private static final Logger logger = LogManager.getLogger(DataLoaderService.class);

    public PathWatcherService(PropertyContainer propertyContainer, AccountService accountService,
                              CustomerService customerService, TransactionService transactionService,
                              ConnectAccountsToCustomersService connectAccountsToCustomersService,
                              FileProcessorService fileProcessor) {
        this.propertyContainer = propertyContainer;
        this.accountService = accountService;
        this.customerService = customerService;
        this.transactionService = transactionService;
        this.connectAccountsToCustomersService = connectAccountsToCustomersService;
        this.fileProcessor = fileProcessor;
    }

    public void watchPaths(ExecutorService executor) {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();

            Path accountsDir = Paths.get(propertyContainer.getAccountsSource());
            accountsDir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            Path customersDir = Paths.get(propertyContainer.getCustomersSource());
            customersDir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            Path transactionsDir = Paths.get(propertyContainer.getTransactionsSource());
            transactionsDir.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

            while (true) {
                WatchKey key = watchService.take();
                for (WatchEvent<?> event : key.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
                        Path filePath = ((Path) key.watchable()).resolve((Path) event.context());
                        File file = filePath.toFile();
                        if (accountsDir.equals(key.watchable())) {
                           logger.info("New accounts file detected: " + file.getName());
                            accountService.run(file);
                            connectAccountsToCustomersService.ConnectAccountToCustomer();
                            fileProcessor.moveFileToBackup(file, propertyContainer.getAccountsOutDestination());
                        } else if (customersDir.equals(key.watchable())) {
                          logger.info("New customers file detected: " + file.getName());
                            executor.submit(() -> {
                                try {
                                    customerService.run(file);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                                fileProcessor.moveFileToBackup(file, propertyContainer.getCustomersOutDestination());
                            });
                        }
                        if (transactionsDir.equals(key.watchable())) {
                           logger.info("New transactions file detected: " + file.getName());
                            executor.submit(() -> {
                                try {
                                    transactionService.run(file);
                                    fileProcessor.moveFileToBackup(file, propertyContainer.getTransactionsOutDestination());
                                } catch (Exception e) {
                                    System.out.println(e.getMessage());
                                }
                            });
                        }
                    }
                }
                key.reset();
            }

        } catch (Exception e) {
            logger.warn("exception in PathWatcher");
        }
    }
}
