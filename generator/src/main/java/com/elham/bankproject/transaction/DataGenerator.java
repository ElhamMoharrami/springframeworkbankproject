package com.elham.bankproject.transaction;

import com.elham.bankproject.common.CsvWriter;
import com.elham.bankproject.model.Account;
import com.elham.bankproject.model.Customer;
import com.elham.bankproject.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataGenerator implements ApplicationContextAware {
    List<Customer> customerList;
    List<Account> accountList;
    private static final Logger logger = LogManager.getLogger(DataGenerator.class);
    private ApplicationContext ctx;

    public void generateCustomer() {
        long startCustomerGenerateTimeMillis = System.currentTimeMillis();
        CustomerGenerator customerGenerator = (CustomerGenerator) ctx.getBean("customerGenerator");
        this.customerList = customerGenerator.generate();
        CsvWriter<Customer> customerCsvWriter = (CsvWriter<Customer>) ctx.getBean("csvWriter",
                "customers.csv", customerGenerator.getFileLocation());
        customerCsvWriter.writeToFile("CustomerId,Name,PostAddress", customerList);
        long endCustomerGenerateTimeMillis = System.currentTimeMillis();
        long timeToGenerateCustomers = endCustomerGenerateTimeMillis - startCustomerGenerateTimeMillis;
        logger.info("customers generated in " + customerGenerator.getFileLocation() + " . took " +
                timeToGenerateCustomers + " milli seconds.");
    }
    public void generateAccounts() {
        long startAccountGenerateTimeMillis = System.currentTimeMillis();
        AccountGenerator accountGenerator = (AccountGenerator) ctx.getBean("accountGenerator", customerList);
        this.accountList = accountGenerator.generate();
        CsvWriter<Account> accountWriter = (CsvWriter<Account>) ctx.getBean("csvWriter", "accounts.csv"
                , accountGenerator.getFileLocation());
        accountWriter.writeToFile("CustomerId,AccountId", accountList);
        long endAccountGenerateTimeMillis = System.currentTimeMillis();
        long timeToGenerateAccounts = endAccountGenerateTimeMillis - startAccountGenerateTimeMillis;
        logger.info("accounts generated in" + accountGenerator.getFileLocation() + " . took " + timeToGenerateAccounts
                + " milli seconds.");
    }

    public void generateTransactions() {
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
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
