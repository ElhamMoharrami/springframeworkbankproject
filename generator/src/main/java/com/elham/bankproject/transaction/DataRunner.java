package com.elham.bankproject.transaction;

import org.springframework.boot.CommandLineRunner;

public class DataRunner implements CommandLineRunner {
    private final DataGenerator dataGenerator;

    public DataRunner(DataGenerator dataGenerator) {
        this.dataGenerator = dataGenerator;
    }

    @Override
    public void run(String... args) {
        dataGenerator.generateCustomer();
        dataGenerator.generateAccounts();
        dataGenerator.generateTransactions();
    }
}
