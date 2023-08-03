package org.elham.bankgenerator.common;

import org.springframework.core.env.Environment;

public class PropertyContainer {
    private final String filesDestination;
    private final String customerCount;
    private final String accountGeneratorMaxBound;
    private final String accountGeneratorMinBound;
    private final String transactionGeneratorMaxBound;
    private final String transactionGeneratorMinBound;
    private final String transactionGeneratorLimit;

    public PropertyContainer(Environment environment) {
        this.filesDestination = environment.getProperty("files.destination");
        this.customerCount = environment.getProperty("customer-generator.customerCount");
        this.accountGeneratorMaxBound = environment.getProperty("accountGenerator.bound.max");
        this.accountGeneratorMinBound = environment.getProperty("accountGenerator.bound.min");
        this.transactionGeneratorMaxBound = environment.getProperty("transaction-generator.transaction.max");
        this.transactionGeneratorMinBound = environment.getProperty("transaction-generator.transaction.min");
        this.transactionGeneratorLimit = environment.getProperty("transaction-generator.transaction.limit");

    }

    public String getFilesDestination() {
        return filesDestination;
    }

    public Integer getCustomerCount() {
        return Integer.parseInt(this.customerCount);
    }

    public Integer getAccountGeneratorMaxBound() {
        return Integer.parseInt(this.accountGeneratorMaxBound);
    }

    public Integer getGetAccountGeneratorMinBound() {
        return Integer.parseInt(this.accountGeneratorMinBound);
    }

    public Integer getTransactionGeneratorMaxBound() {
        return Integer.parseInt(this.transactionGeneratorMaxBound);
    }

    public Integer getTransactionGeneratorMinBound() {
        return Integer.parseInt(this.transactionGeneratorMinBound);
    }

    public Integer getGetTransactionGeneratorLimit() {
        return Integer.parseInt(this.transactionGeneratorLimit);
    }
}
