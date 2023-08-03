package org.elham.bankLoader.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
public class DataLoaderService implements CommandLineRunner {
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private ConnectAccountsToCustomersService connectAccountsToCustomersService;


    @Override
    public void run(String... args) throws Exception {
        customerService.run();
        accountService.run();
        connectAccountsToCustomersService.ConnectAccountToCustomer();
        transactionService.run();
    }
}
