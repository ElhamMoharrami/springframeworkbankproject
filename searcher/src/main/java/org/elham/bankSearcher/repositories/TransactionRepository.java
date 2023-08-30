package org.elham.bankSearcher.repositories;

import org.elham.bankSearcher.model.TransactionProjection;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<TransactionProjection, String> {
    @Query("SELECT new TransactionProjection(c.name,c.customerId,a.accountId,t.id, t.accAId, t.accBId, t.amount, t.type) " +
            "            FROM Customer c  " +
            "            INNER JOIN Account a  ON c.customerId = a.customerId " +
            "            INNER JOIN Transaction t ON a.accountId = t.accAId OR a.accountId = t.accBId " +
            "            WHERE c.name =:customerName")
    List<TransactionProjection> findByCustomer(@Param("customerName") String customerName);
}