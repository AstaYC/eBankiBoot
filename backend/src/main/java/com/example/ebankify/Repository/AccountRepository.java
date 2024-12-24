package com.example.ebankify.Repository;

import com.example.ebankify.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends GenericRepository<Account> {
}
