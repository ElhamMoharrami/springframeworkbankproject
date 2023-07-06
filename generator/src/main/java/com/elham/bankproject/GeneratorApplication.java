package com.elham.bankproject;

import com.elham.bankproject.common.ConfigLoader;
import com.elham.bankproject.common.CsvWriter;
import com.elham.bankproject.model.Account;
import com.elham.bankproject.model.Customer;
import com.elham.bankproject.transactionGenerator.AccountGenerator;
import com.elham.bankproject.transactionGenerator.CustomerGenerator;
import com.elham.bankproject.transactionGenerator.TransactionGenerator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class GeneratorApplication {
    private static final Logger logger = LogManager.getLogger(GeneratorApplication.class);

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(GeneratorApplication.class, args);
        try {
            ConfigLoader loadConfig = (ConfigLoader) ctx.getBean("configLoader");
            String fileLoc = loadConfig.loadConfig("files.destination");
            long startCustomerGenerateTimeMillis = System.currentTimeMillis();
            Integer customerCount = Integer.parseInt(loadConfig.loadConfig("customergenerator.customerCount"));
            CustomerGenerator customerGenerator = (CustomerGenerator) ctx.getBean("customerGenerator", customerCount);
            List<Customer> customerList = customerGenerator.generateCustomers();
            String fileName = "customers.csv";
            CsvWriter<Customer> customerCsvWriter = (CsvWriter<Customer>) ctx.getBean("csvWriter", fileName, fileLoc);
            customerCsvWriter.writeToFile("CustomerId,Name,PostAddress", customerList);
            long endCustomerGenerateTimeMillis = System.currentTimeMillis();
            long timeToGenerateCustomers = endCustomerGenerateTimeMillis - startCustomerGenerateTimeMillis;
            logger.info("customers generated in " + fileLoc + " . took " + timeToGenerateCustomers + " milli seconds.");
            long startAccountGenerateTimeMillis = System.currentTimeMillis();
            AccountGenerator accountGenerator = (AccountGenerator) ctx.getBean("accountGenerator", customerList);
            List<Account> accountList = accountGenerator.generateAccount();
            CsvWriter<Account> accountWriter = (CsvWriter<Account>) ctx.getBean("csvWriter", "accounts.csv",
                    fileLoc);
            accountWriter.writeToFile("CustomerId,AccountId", accountList);
            long endAccountGenerateTimeMillis = System.currentTimeMillis();
            long timeToGenerateAccounts = endAccountGenerateTimeMillis - startAccountGenerateTimeMillis;
            logger.info("accounts generated in " + fileLoc + " . took " + timeToGenerateAccounts + " milli seconds.");
            int transactionMinBound = Integer.parseInt(loadConfig.loadConfig
                    ("transactiongenerator.transaction.min"));
            int transactionMaxBound = Integer.parseInt(loadConfig.loadConfig
                    ("transactiongenerator.transaction.max"));
            TransactionGenerator transactionGenerator = (TransactionGenerator) ctx.getBean("transactionGenerator",
                    accountList, transactionMinBound,
                    transactionMaxBound);
            transactionGenerator.generateTransaction();
        } catch (ArrayIndexOutOfBoundsException e) {
            logger.error("No directory Entered. Please Enter a Directory and the path to config file.");
        } catch (NumberFormatException e) {
            logger.error("couldn't get value from config file");
        }
    }


}

