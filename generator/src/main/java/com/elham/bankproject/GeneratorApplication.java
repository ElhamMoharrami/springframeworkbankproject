package com.elham.bankproject;

import com.elham.bankproject.common.CsvWriter;
import com.elham.bankproject.model.Account;
import com.elham.bankproject.model.Customer;
import com.elham.bankproject.model.Transaction;
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
            long startCustomerGenerateTimeMillis = System.currentTimeMillis();
            CustomerGenerator customerGenerator = (CustomerGenerator) ctx.getBean("customerGenerator");
            List<Customer> customerList = customerGenerator.generate();
            CsvWriter<Customer> customerCsvWriter = (CsvWriter<Customer>) ctx.getBean("csvWriter",
                    "customers.csv", customerGenerator.getFileLocation());
            customerCsvWriter.writeToFile("CustomerId,Name,PostAddress", customerList);
            long endCustomerGenerateTimeMillis = System.currentTimeMillis();
            long timeToGenerateCustomers = endCustomerGenerateTimeMillis - startCustomerGenerateTimeMillis;
            logger.info("customers generated . took " + timeToGenerateCustomers + " milli seconds.");

            long startAccountGenerateTimeMillis = System.currentTimeMillis();
            AccountGenerator accountGenerator = (AccountGenerator) ctx.getBean("accountGenerator", customerList);
            List<Account> accountList = accountGenerator.generate();
            CsvWriter<Account> accountWriter = (CsvWriter<Account>) ctx.getBean("csvWriter", "accounts.csv"
                    , accountGenerator.getFileLocation());
            accountWriter.writeToFile("CustomerId,AccountId", accountList);
            long endAccountGenerateTimeMillis = System.currentTimeMillis();
            long timeToGenerateAccounts = endAccountGenerateTimeMillis - startAccountGenerateTimeMillis;
            logger.info("accounts generated in . took " + timeToGenerateAccounts
                    + " milli seconds.");

            TransactionGenerator transactionGenerator = (TransactionGenerator) ctx.getBean("transactionGenerator",
                    accountList);
            List<Transaction> transactionList = transactionGenerator.generate();
            int transactionLimit = transactionGenerator.getTransactionLimit();
            int trListSize = transactionList.size();
            for (int i = 0; i < trListSize; i += transactionLimit) {
                int end = Math.min(trListSize, i + transactionLimit);
                List<Transaction> sublist = transactionList.subList(i, end);
                CsvWriter<Transaction> transactionWriter = new CsvWriter<>("transaction" + i + ".csv",
                        transactionGenerator.getFileLocation());
                transactionWriter.writeToFile("TransactionId,EpochTime,Amount,SourceAcc,DestinationAcc,Type",
                        sublist);
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            logger.error("No directory Entered. Please Enter a Directory and the path to config file.");
        } catch (NumberFormatException e) {
            logger.error("couldn't get value from config file");
        }
    }
}

