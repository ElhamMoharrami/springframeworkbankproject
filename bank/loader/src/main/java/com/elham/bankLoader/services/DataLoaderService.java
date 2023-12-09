package com.elham.bankLoader.services;

import com.elham.bankLoader.resource.PropertyContainer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class DataLoaderService implements CommandLineRunner {
    private final PropertyContainer propertyContainer;
    private final FileProcessorService fileProcessor;
    private final PathWatcherService pathWatcher;
    private static final Logger logger = LogManager.getLogger(DataLoaderService.class);

    public DataLoaderService(PropertyContainer propertyContainer, FileProcessorService fileProcessor,
                             PathWatcherService pathWatcher) {
        this.propertyContainer = propertyContainer;
        this.fileProcessor = fileProcessor;
        this.pathWatcher = pathWatcher;
    }

    @Override
    public void run(String... args) {
        try {
            ExecutorService executor = Executors.newFixedThreadPool(3);
            while (true) {
                fileProcessor.processFiles(propertyContainer.getCustomersSource(),
                        propertyContainer.getCustomersOutDestination(), "customers");
                fileProcessor.loadTransaction(executor, propertyContainer.getTransactionsSource(),
                        propertyContainer.getTransactionsOutDestination());
                pathWatcher.watchPaths(executor);
            }
        } catch (Exception e) {
            logger.warn("exception happened in data loader service.");
            System.out.println(e.getMessage());
        }

    }
}
