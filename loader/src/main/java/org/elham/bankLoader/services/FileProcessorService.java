package org.elham.bankLoader.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elham.bankLoader.model.PropertyContainer;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.ExecutorService;

@Service
public class FileProcessorService {
    private final TransactionService transactionService;

    private final CustomerService customerService;

    private final AccountService accountService;

    private final ConnectAccountsToCustomersService connectAccountsToCustomersService;

    private final PropertyContainer propertyContainer;

    private static final Logger logger = LogManager.getLogger(FileProcessorService.class);


    public FileProcessorService(TransactionService transactionService, CustomerService customerService,
                                AccountService accountService,
                                ConnectAccountsToCustomersService connectAccountsToCustomersService,
                                PropertyContainer propertyContainer) {
        this.transactionService = transactionService;
        this.customerService = customerService;
        this.accountService = accountService;
        this.connectAccountsToCustomersService = connectAccountsToCustomersService;
        this.propertyContainer = propertyContainer;
    }

    public void processFiles(String srcPath, String destPath, String type) {
        try {
            File folder = new File(srcPath);
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        if (type.equals("customers")) {
                            logger.info("New customers file detected: " + file.getName());
                            customerService.run(file);
                            moveFileToBackup(file, destPath);
                            processFiles(propertyContainer.getAccountsSource(),
                                    propertyContainer.getAccountsOutDestination(), "accounts");
                        }
                        if (type.equals("accounts")) {
                            logger.info("New account file detected: " + file.getName());
                            accountService.run(file);
                            connectAccountsToCustomersService.ConnectAccountToCustomer();
                            moveFileToBackup(file, destPath);
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.warn("InterruptedException in file processor service");
        } catch (Exception e) {
            logger.warn("exception in file processor service");
        }
    }

    public void loadTransaction(ExecutorService executor, String srcPath, String destPath) {
        try {
            File folder = new File(srcPath);
            File[] files = folder.listFiles();
            if (files != null) {
                for (File trFile : files) {
                    String fileName = trFile.getName();
                    String newFileName = fileName.substring(0, fileName.lastIndexOf(".")) + "threaded.csv";
                    String parentPath = trFile.getParent();
                    File renamedFile = new File(parentPath, newFileName);
                    if (renamedFile.isFile() && renamedFile.exists()) {
                        executor.submit(() -> {
                            try {
                                transactionService.run(renamedFile);
                                moveFileToBackup(renamedFile, destPath);
                            } catch (Exception e) {
                                System.out.println(e.getMessage());
                            }
                        });
                    }
                }
            }
        } catch (Exception e) {
            logger.warn("exception in load transaction in file processor");
        }
    }

    public void moveFileToBackup(File file, String destPath) {
        try {
            Path sourcePath = file.toPath();
            Path backupPath = Paths.get(destPath, file.getName());
            Files.move(sourcePath, backupPath);
            logger.info("Moved file " + file.getName() + " to backup directory.");
        } catch (IOException e) {
            logger.warn("IOException in move file to backup.");
        }
    }
}
