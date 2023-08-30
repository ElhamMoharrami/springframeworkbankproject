package org.elham.bankSearcher.model;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @Column(name = "transaction_id")
    private String id;
    @Column(name = "transaction_time")
    private String time;
    @Column(name = "amount")
    private double amount;
    @Column(name = "source_id")
    private String accAId;
    @Column(name = "destination_id")
    private String accBId;
    @Enumerated(value = EnumType.STRING)
    private TransactType type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getAccAId() {
        return accAId;
    }

    public void setAccAId(String accAId) {
        this.accAId = accAId;
    }

    public String getAccBId() {
        return accBId;
    }

    public void setAccBId(String accBId) {
        this.accBId = accBId;
    }

    public TransactType getType() {
        return type;
    }

    public void setType(TransactType type) {
        this.type = type;
    }
}
