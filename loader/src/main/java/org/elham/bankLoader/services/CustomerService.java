package org.elham.bankLoader.services;

import org.elham.bankLoader.model.Customer;
import org.elham.bankLoader.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.FileReader;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Value("${files.destination}")
    private String fileDestination;

    public void run() throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(ResourceUtils.getFile(fileDestination+"/customers.csv")));
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
    }
}
