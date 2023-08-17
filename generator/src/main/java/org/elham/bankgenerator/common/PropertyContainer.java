package org.elham.bankgenerator.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("generator-config")
public class PropertyContainer {
    private String fileDestination;
    private int customerCount;
    private int accountMin;
    private int accountMax;
    private int transactionMax;
    private int transactionMin;
    private int transactionLimit;

    public String getFileDestination() {
        return fileDestination;
    }

    public void setFileDestination(String filesDestination) {
        this.fileDestination = filesDestination;
    }

    public int getCustomerCount() {
        return customerCount;
    }

    public void setCustomerCount(int customerCount) {
        this.customerCount = customerCount;
    }

    public int getAccountMin() {
        return accountMin;
    }

    public void setAccountMin(int accountMin) {
        this.accountMin = accountMin;
    }

    public int getAccountMax() {
        return accountMax;
    }

    public void setAccountMax(int accountMax) {
        this.accountMax = accountMax;
    }

    public int getTransactionMax() {
        return transactionMax;
    }

    public void setTransactionMax(int transactionMax) {
        this.transactionMax = transactionMax;
    }

    public int getTransactionMin() {
        return transactionMin;
    }

    public void setTransactionMin(int transactionMin) {
        this.transactionMin = transactionMin;
    }

    public int getTransactionLimit() {
        return transactionLimit;
    }

    public void setTransactionLimit(int transactionLimit) {
        this.transactionLimit = transactionLimit;
    }
}