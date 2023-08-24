package org.elham.bankLoader.services;

import org.elham.bankLoader.model.Account;
import org.elham.bankLoader.model.Customer;
import org.elham.bankLoader.repositories.AccountRepository;
import org.elham.bankLoader.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ConnectAccountsToCustomersService {
    private final CustomerRepository customerRepository;

    private final AccountRepository accountRepository;

    public ConnectAccountsToCustomersService(CustomerRepository customerRepository, AccountRepository accountRepository) {
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
    }

    public void ConnectAccountToCustomer() {
        List<Customer> customersList = customerRepository.findAll();
        List<Account> accountsList = (List<Account>) accountRepository.findAll();
        Map<String, Customer> customerMap = customersList.stream()
                .collect(Collectors.toMap(Customer::getCustomerId, Function.identity()));
        for (Account account : accountsList) {
            String customerId = account.getCustomerId();
            Customer customer = customerMap.get(customerId);
            customer.addAccount(account);
        }
    }
}
