package org.elham.bankgenerator.common;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("generator-config")
public class PropertyContainer {
    private String customerFileDestination;

    private String accountFileDestination;

    private String transactionFileDestination;
    private int customerCount;
    private int accountMin;
    private int accountMax;
    private int transactionMax;
    private int transactionMin;
    private int transactionLimit;


    public int getCustomerCount() {
        return customerCount;
    }

    public String getCustomerFileDestination() {
        return customerFileDestination;
    }

    public void setCustomerFileDestination(String customerFileDestination) {
        this.customerFileDestination = customerFileDestination;
    }

    public String getAccountFileDestination() {
        return accountFileDestination;
    }

    public void setAccountFileDestination(String accountFileDestination) {
        this.accountFileDestination = accountFileDestination;
    }

    public String getTransactionFileDestination() {
        return transactionFileDestination;
    }

    public void setTransactionFileDestination(String transactionFileDestination) {
        this.transactionFileDestination = transactionFileDestination;
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