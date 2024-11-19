package com.example.ebankify.Service;

import com.example.ebankify.DTO.AccountDTO;

import java.util.List;

public interface AccountService {
    AccountDTO createAccount(AccountDTO accountDTO);
    AccountDTO getAccountById(Long id);
    List<AccountDTO> getAllAccounts();
    AccountDTO updateAccount(Long id, AccountDTO accountDTO);
    boolean deleteAccount(Long id);
    boolean existsById(Long id);

}
