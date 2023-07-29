package com.elham.bankproject.transaction;

import com.elham.bankproject.model.Account;
import com.elham.bankproject.model.Transaction;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TransactionGenerator extends AbstractGenerator<Transaction> {

    List<Transaction> transactionList = new ArrayList<>();

    private final List<Account> accounts;

    public TransactionGenerator(List<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public List<Transaction> generate() {
        Random random = new Random();
        List<Integer> accountIds = AccountGenerator.getAccIds();
        int countTransactions = random.nextInt(super.getPropertyContainer().getTransactionGeneratorMinBound() +
                super.getPropertyContainer().getTransactionGeneratorMaxBound()) +
                super.getPropertyContainer().getTransactionGeneratorMaxBound();
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
        return super.getPropertyContainer().getGetTransactionGeneratorLimit();
    }
}
