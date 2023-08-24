package org.elham.bankLoader.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.elham.bankLoader.model.Customer;
import org.elham.bankLoader.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    private static final Logger logger = LogManager.getLogger(CustomerService.class);

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void run(File file) throws Exception {
        long startCustomerLoadTimeMillis = System.currentTimeMillis();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        boolean isFirstRow = true;
        while ((line = reader.readLine()) != null) {
            if (isFirstRow) {
                isFirstRow = false;
                continue;
            }
            String[] columns = line.split(",");
            Customer csvData = new Customer();
            csvData.setCustomerId(columns[0]);
            csvData.setName(columns[1]);
            csvData.setAddress(columns[2]);
            customerRepository.save(csvData);
        }
        reader.close();
        long endCustomerLoadTimeMillis = System.currentTimeMillis();
        long timeToLoadCustomers = endCustomerLoadTimeMillis - startCustomerLoadTimeMillis;
        logger.info("customers loaded in database. took " + timeToLoadCustomers + " milli seconds.");
    }
}