package org.elham.bankgenerator.transaction;

import org.elham.bankgenerator.model.Account;
import org.elham.bankgenerator.model.Customer;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AccountGenerator extends AbstractGenerator<Account> {

    private static final List<Long> accIds = new ArrayList<>();
    private static final List<Account> accounts = new ArrayList<>();
    private final List<Customer> customersList;

    public AccountGenerator(List<Customer> customersList) {
        this.customersList = customersList;
    }

    public static List<Long> getAccIds() {
        return accIds;
    }

    @Override
    public List<Account> generate() {
        int accountMinBound = super.getPropertyContainer().getAccountMin();
        int accountMaxBound = super.getPropertyContainer().getAccountMax();
        Random random = new Random();
        Long accountId = 1L;
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
