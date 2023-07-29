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
    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    CsvWriter<?> csvWriter(String fileName) {
        return new CsvWriter<>(fileName);
    }

    @Bean
    ConfigLoader configLoader() {
        return new ConfigLoader();
    }

    @Bean
    CustomerGenerator customerGenerator() {
        return new CustomerGenerator();
    }

    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    AccountGenerator accountGenerator(List<Customer> customersList) {
        return new AccountGenerator(customersList);
    }

    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    TransactionGenerator transactionGenerator(List<Account> accounts) {
        return new TransactionGenerator(accounts);
    }

    @Bean
    PropertyContainer propertyContainer(Environment environment) {
        return new PropertyContainer(environment);
    }

    @Bean
    DataGenerator dataGenerator() {
        return new DataGenerator();
    }

    @Bean
    DataRunner dataRunner(DataGenerator dataGenerator) {
        return new DataRunner(dataGenerator);
    }
}
