package org.elham.bankgenerator.config;

import org.elham.bankgenerator.common.CsvWriter;
import org.elham.bankgenerator.common.PropertyContainer;
import org.elham.bankgenerator.model.Account;
import org.elham.bankgenerator.model.Customer;
import org.elham.bankgenerator.transaction.*;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import java.util.List;

@Configuration
public class AnnotationConfig {

    @Bean
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    CsvWriter<?> csvWriter(String fileName) {
        return new CsvWriter<>(fileName);
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
    PropertyContainer propertyContainer() {
        return new PropertyContainer();
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