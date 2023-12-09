package org.elham.bankgenerator.transaction;


import com.elham.bank.common.CsvWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.elham.bankgenerator.common.PropertyContainer;
import org.elham.bankgenerator.model.Account;
import org.elham.bankgenerator.model.Customer;
import org.elham.bankgenerator.model.Transaction;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

public class DataGenerator implements ApplicationContextAware {
    public DataGenerator(PropertyContainer propertyContainer) {
        this.propertyContainer = propertyContainer;
    }

    private final PropertyContainer propertyContainer;
    List<Customer> customerList;
    List<Account> accountList;
    private static final Logger logger = LogManager.getLogger(DataGenerator.class);
    private ApplicationContext ctx;

    public void generateCustomer() {
        long startCustomerGenerateTimeMillis = System.currentTimeMillis();
        CustomerGenerator customerGenerator = (CustomerGenerator) ctx.getBean("customerGenerator");
        this.customerList = customerGenerator.generate();
        String fileLocation = propertyContainer.getCustomerFileDestination();
        CsvWriter<Customer> customerCsvWriter = new CsvWriter<>();
        customerCsvWriter.writeToFile("CustomerId,Name,PostAddress", customerList, "customers.csv", fileLocation);
        long endCustomerGenerateTimeMillis = System.currentTimeMillis();
        long timeToGenerateCustomers = endCustomerGenerateTimeMillis - startCustomerGenerateTimeMillis;
        logger.info("customers generated in " + fileLocation + " . took " +
                timeToGenerateCustomers + " milli seconds.");
    }

    public void generateAccounts() {
        long startAccountGenerateTimeMillis = System.currentTimeMillis();
        AccountGenerator accountGenerator = (AccountGenerator) ctx.getBean("accountGenerator", customerList);
        this.accountList = accountGenerator.generate();
        String fileLocation = propertyContainer.getAccountFileDestination();
        CsvWriter<Account> accountCsvWriter = new CsvWriter<>();
        accountCsvWriter.writeToFile("CustomerId,AccountId", accountList, "accounts.csv", fileLocation);
        long endAccountGenerateTimeMillis = System.currentTimeMillis();
        long timeToGenerateAccounts = endAccountGenerateTimeMillis - startAccountGenerateTimeMillis;
        logger.info("accounts generated in" + fileLocation + " . took " + timeToGenerateAccounts
                + " milli seconds.");
    }

    public void generateTransactions() {
        TransactionGenerator transactionGenerator = (TransactionGenerator) ctx.getBean("transactionGenerator", accountList);
        List<Transaction> transactionList = transactionGenerator.generate();
        int transactionLimit = transactionGenerator.getTransactionLimit();
        int trListSize = transactionList.size();
        CsvWriter<Transaction> transactionCsvWriter = new CsvWriter<>();
        String fileLocation = propertyContainer.getTransactionFileDestination();
        for (int i = 0; i < trListSize; i += transactionLimit) {
            int end = Math.min(trListSize, i + transactionLimit);
            List<Transaction> sublist = transactionList.subList(i, end);
            transactionCsvWriter.writeToFile("TransactionId,EpochTime,Amount,SourceAcc,DestinationAcc,Type",
                    sublist, "transaction" + i + ".csv", fileLocation);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}