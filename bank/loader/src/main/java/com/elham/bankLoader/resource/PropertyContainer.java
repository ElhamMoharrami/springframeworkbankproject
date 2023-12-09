package com.elham.bankLoader.resource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("loader-config")
public class PropertyContainer {
    private String customersSource;
    private String accountsSource;
    private String transactionsSource;

    private String customersOutDestination;

    private String accountsOutDestination;

    private String transactionsOutDestination;

    public String getCustomersSource() {
        return customersSource;
    }

    public void setCustomersSource(String customersSource) {
        this.customersSource = customersSource;
    }

    public String getAccountsSource() {
        return accountsSource;
    }

    public void setAccountsSource(String accountsSource) {
        this.accountsSource = accountsSource;
    }

    public String getTransactionsSource() {
        return transactionsSource;
    }

    public void setTransactionsSource(String transactionsSource) {
        this.transactionsSource = transactionsSource;
    }

    public String getCustomersOutDestination() {
        return customersOutDestination;
    }

    public void setCustomersOutDestination(String customersOutDestination) {
        this.customersOutDestination = customersOutDestination;
    }

    public String getAccountsOutDestination() {
        return accountsOutDestination;
    }

    public void setAccountsOutDestination(String accountsOutDestination) {
        this.accountsOutDestination = accountsOutDestination;
    }

    public String getTransactionsOutDestination() {
        return transactionsOutDestination;
    }

    public void setTransactionsOutDestination(String transactionsOutDestination) {
        this.transactionsOutDestination = transactionsOutDestination;
    }
}
