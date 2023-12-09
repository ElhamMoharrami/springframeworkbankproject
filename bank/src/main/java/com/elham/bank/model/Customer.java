package org.elham.bankSearcher.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
public class Customer {
    @Id
    @Column(name = "customer_id")
    private Long customerId;
    @Column(name = "customer_name")
    private String name;
    @Column(name = "customer_address")
    private String address;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Account> accounts = new ArrayList<>();

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void addAccount(Account account) {
        account.setCustomer(this);
        accounts.add(account);
    }

    public void removeAccount(Account account) {
        account.setCustomer(null);
        accounts.remove(account);
    }
}
