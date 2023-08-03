package org.elham.bankgenerator.common;

import java.util.Random;

public enum TransactType {
    CREDIT, DEBIT;
    private static final Random rand = new Random();
    public static TransactType randomType() {
        TransactType[] transactionType = values();
        return transactionType[rand.nextInt(transactionType.length)];
    }
}