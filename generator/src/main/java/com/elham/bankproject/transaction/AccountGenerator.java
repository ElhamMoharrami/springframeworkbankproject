package com.elham.bankproject.transaction;

import com.elham.bankproject.model.Account;
import com.elham.bankproject.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AccountGenerator extends AbstractGenerator<Account> {

    private static final List<Integer> accIds = new ArrayList<>();
    private static final List<Account> accounts = new ArrayList<>();
    private final List<Customer> customersList;

    public AccountGenerator(List<Customer> customersList) {
        this.customersList = customersList;
    }

    public static List<Integer> getAccIds() {
        return accIds;
    }

    @Override
    public List<Account> generate() {
        int accountMinBound = super.getPropertyContainer().getGetAccountGeneratorMinBound();
        int accountMaxBound = super.getPropertyContainer().getAccountGeneratorMaxBound();
        Random random = new Random();
        int accountId = 1;
        for (Customer customer : customersList) {
            int countAccounts = random.nextInt(accountMaxBound) + accountMinBound;
            for (int j = 1; j <= countAccounts; j++) {
                accIds.add(accountId);
                Account account = new Account(customer.getCustomerId(), accountId);
                accounts.add(account);
                accountId += 1;
            }
        }
        return accounts;
    }
}
