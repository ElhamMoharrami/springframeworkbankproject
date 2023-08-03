package org.elham.bankLoader.model;

import javax.persistence.*;

@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @Column(name = "transaction_id")
    private Long id;
    @Column(name = "transaction_time")
    private Long time;
    @Column(name = "amount")
    private double amount;
    @Column(name = "source_id")
    private Long accAId;
    @Column(name = "destination_id")
    private Long accBId;
    @Enumerated(value = EnumType.STRING)
    private TransactType type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Long getAccAId() {
        return accAId;
    }

    public void setAccAId(Long accAId) {
        this.accAId = accAId;
    }

    public Long getAccBId() {
        return accBId;
    }

    public void setAccBId(Long accBId) {
        this.accBId = accBId;
    }

    public TransactType getType() {
        return type;
    }

    public void setType(TransactType type) {
        this.type = type;
    }
}
