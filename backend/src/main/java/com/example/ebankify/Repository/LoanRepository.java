package com.example.ebankify.Repository;

import com.example.ebankify.Entity.Loan;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends GenericRepository<Loan>{
    List<Loan> findByUserId(long id);
}
