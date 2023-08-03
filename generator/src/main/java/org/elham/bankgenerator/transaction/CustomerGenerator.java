package org.elham.bankgenerator.transaction;




import org.elham.bankgenerator.model.Customer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CustomerGenerator extends AbstractGenerator<Customer> {

    @Override
    public List<Customer> generate() {
        final List<String> fName = Arrays.asList("Julian", "Dante", "Jacks", "Scarlet", "Tella", "Nicolas");
        final List<String> lName = Arrays.asList("Santos", "Dragna", "Duarte", "Green", "Blake", "Roans");
        List<Customer> customers = new ArrayList<>();
        int customerCount = super.getPropertyContainer().getCustomerCount();
        for (int i = 1; i <= customerCount; i++) {
            long customerId = Long.parseLong(String.valueOf(i));
            Random random = new Random();
            String name = fName.get(random.nextInt(fName.size())) + " " + lName.get(random.nextInt(lName.size()));
            long address = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
            customers.add(new Customer(customerId, name, String.valueOf(address)));
        }
        return customers;
    }
}