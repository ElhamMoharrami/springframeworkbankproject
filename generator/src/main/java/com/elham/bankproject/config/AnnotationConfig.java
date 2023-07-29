package com.elham.bankproject.config;

import com.elham.bankproject.common.ConfigLoader;
import com.elham.bankproject.common.CsvWriter;
import com.elham.bankproject.common.PropertyContainer;
import com.elham.bankproject.model.Account;
import com.elham.bankproject.model.Customer;
import com.elham.bankproject.transaction.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;

import java.util.List;

@Configuration
public class AnnotationConfig {
    @Bean(name = "csvWriter")
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    CsvWriter<?> getWriter(String fileName) {
        return new CsvWriter<>(fileName);
    }

    @Bean(name = "configLoader")
    ConfigLoader getConfigLoader() {
        return new ConfigLoader();
    }

    @Bean(name = "customerGenerator")
    CustomerGenerator getCustomerGenerator() {
        return new CustomerGenerator();
    }

    @Bean(name = "accountGenerator")
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    AccountGenerator getAccountGenerator(List<Customer> customersList) {
        return new AccountGenerator(customersList);
    }

    @Bean(name = "transactionGenerator")
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    TransactionGenerator getTransactionGenerator(List<Account> accounts) {
        return new TransactionGenerator(accounts);
    }

    @Bean(name = "propertyContainer")
    PropertyContainer gerPropertyContainer(Environment environment) {
        return new PropertyContainer(environment);
    }

    @Bean(name = "dataGenerator")
    DataGenerator getDataGenerator() {
        return new DataGenerator();
    }

    @Bean(name = "dataRunner")
    DataRunner getDataRunner(DataGenerator dataGenerator) {
        return new DataRunner(dataGenerator);
    }
}
