package com.example.ebankify.Repository;

import com.example.ebankify.Entity.Transaction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends GenericRepository<Transaction>{
    List<Transaction> findBySourceAccountIdOrDestinationAccountId(Long sourceAccountId, Long destinationAccountId);
    Transaction findBySourceAccountIdOrDestinationAccountIdAndId(Long sourceAccountId, Long destinationAccountId,Long id);
}
