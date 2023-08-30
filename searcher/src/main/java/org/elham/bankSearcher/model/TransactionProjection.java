package org.elham.bankSearcher.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TransactionProjection {
    private String customerName;
    private String customerId;
    private String accountId;
    private String transactionId;
    private String sourceAccountId;
    private String destinationAccountId;
    private double amount;
    private TransactType type;
    @Id
    private Long id;

    public TransactionProjection(String customerName, String customerId, String accountId, String transactionId,
                                 String sourceAccountId, String destinationAccountId, double amount, TransactType type) {
        this.customerName = customerName;
        this.customerId = customerId;
        this.accountId = accountId;
        this.transactionId = transactionId;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
        this.type = type;
    }

    public TransactionProjection() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return
                customerName +
                        "," + customerId +
                        "," + accountId +
                        "," + transactionId +
                        "," + sourceAccountId +
                        "," + destinationAccountId +
                        "," + amount +
                        "," + type;
    }
}