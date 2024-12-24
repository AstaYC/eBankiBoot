package com.example.ebankify.Service.Implementation;

import com.example.ebankify.DTO.AccountDTO;
import com.example.ebankify.Entity.Account;
import com.example.ebankify.Entity.User;
import com.example.ebankify.Repository.AccountRepository;
import com.example.ebankify.Repository.UserRepository;
import com.example.ebankify.Service.AccountService;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final ModelMapper AccountMapper;

    public AccountServiceImpl(AccountRepository accountRepository, UserRepository userRepository,ModelMapper AccountMapper) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.AccountMapper=AccountMapper;
    }

    @Override
    public AccountDTO createAccount(AccountDTO accountDTO) {
        Optional<User> userOptional = userRepository.findById(accountDTO.getUserId());
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        Account account = AccountMapper.map(accountDTO,Account.class);
        Account savedAccount = accountRepository.save(account);
        return convertToDto(savedAccount);
    }

    @Override
    public AccountDTO getAccountById(Long id) {
        return accountRepository.findById(id)
                .map(this::convertToDto)
                .orElse(null); // or throw custom exception for not found
    }

    @Override
    public List<AccountDTO> getAllAccounts() {
        return accountRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDTO updateAccount(Long id, AccountDTO accountDTO) {
        Optional<Account> accountOptional = accountRepository.findById(id);
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            account.setBalance(accountDTO.getBalance());
            account.setStatus(accountDTO.getStatus());
            Account updatedAccount = accountRepository.save(account);
            return convertToDto(updatedAccount);
        } else {
            return null; // or throw custom exception for not found
        }
    }

    @Override
    public boolean deleteAccount(Long id) {
        if (accountRepository.existsById(id)) {
            accountRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean existsById(Long id) {
        return accountRepository.existsById(id);
    }

    // Utility method to convert Account entity to AccountDTO
    private AccountDTO convertToDto(Account account) {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setId(account.getId());
        accountDTO.setBalance(account.getBalance());
        accountDTO.setStatus(account.getStatus());
        accountDTO.setUserId(account.getUser().getId());
        return accountDTO;
    }
}

