package com.elham.bankproject.config;

import com.elham.bankproject.common.ConfigLoader;
import com.elham.bankproject.common.CsvWriter;
import com.elham.bankproject.model.Account;
import com.elham.bankproject.model.Customer;
import com.elham.bankproject.transactionGenerator.AccountGenerator;
import com.elham.bankproject.transactionGenerator.CustomerGenerator;
import com.elham.bankproject.transactionGenerator.TransactionGenerator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;

import java.util.List;

@Configuration
@PropertySource("classpath:application.properties")
public class AnnotationConfig {
    @Bean(name = "customerGenerator")
    CustomerGenerator customerGenerator() {
        return new CustomerGenerator();
    }

    @Bean(name = "csvWriter")
    @Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
    CsvWriter getWriter(String fileName,String fileLocation) {
        return new CsvWriter(fileName,fileLocation);
    }

    @Bean(name = "configLoader")
    ConfigLoader getConfigLoader() {
        return new ConfigLoader();
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
}