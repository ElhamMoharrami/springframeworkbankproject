package com.elham.bankproject.transactionGenerator;

import com.elham.bankproject.common.CsvWriter;
import com.elham.bankproject.model.Account;
import com.elham.bankproject.model.Transaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TransactionGenerator extends AbstractGenerator<Transaction> {
    private static final Logger logger = LogManager.getLogger(TransactionGenerator.class);

    @Value("${transactiongenerator.transaction.limit}")
    private String transactionLimit;

    @Value("${transactiongenerator.transaction.min}")
    public int transactionMinBound;

    @Value("${transactiongenerator.transaction.max}")
    public int transactionMaxBound;

    List<Transaction> transactionList = new ArrayList<>();

    private final List<Account> accounts;

    public TransactionGenerator(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public List<Transaction> generate() {
        Random random = new Random();
        List<Integer> accountIds = AccountGenerator.getAccIds();
        int countTransactions = random.nextInt(this.transactionMinBound + this.transactionMaxBound) +
                this.transactionMaxBound;
        long transactionId = 0;
        for (Account account : accounts) {
            for (int i = 0; i < countTransactions; i++) {
                long now = System.currentTimeMillis();
                long timeSinceTransaction = random.nextInt(14 * 24 * 60 * 60 * 1000);
                long date = now - timeSinceTransaction;
                double amount = Double.parseDouble(getRandomValue());
                long accB = accountIds.get((int) (Math.random() * accountIds.size()));
                String type = TransactType.randomType().toString();
                if (accB == account.getAccountId()) {
                    Transaction transactionF = new Transaction(transactionId += 1, date, amount, account.getAccountId(),
                            accB, "Failed");
                    transactionList.add(transactionF);
                } else {
                    Transaction transactionA = new Transaction(transactionId += 2, date, amount, account.getAccountId(),
                            accB, type);
                    transactionList.add(transactionA);
                    Transaction transactionB = new Transaction(transactionId += 3, date, amount, accB,
                            account.getAccountId(), type.equals("CREDIT") ? "DEBIT" : "CREDIT");
                    transactionList.add(transactionB);
                }
            }
        }
        return transactionList;
    }

    public static String getRandomValue() {
        final Random random = new Random();
        final double dbl = random.nextDouble() * (1000 - 1) + 1;
        return String.format("%." + 2 + "f", dbl);
    }

    public Integer getTransactionLimit() {
        return Integer.parseInt(transactionLimit);
    }
}
