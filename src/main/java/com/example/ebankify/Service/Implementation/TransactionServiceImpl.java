package com.example.ebankify.Service.Implementation;

import com.example.ebankify.DTO.TransactionDTO;
import com.example.ebankify.Entity.Account;
import com.example.ebankify.Entity.Enums.TransactionStatus;
import com.example.ebankify.Entity.Transaction;
import com.example.ebankify.Repository.AccountRepository;
import com.example.ebankify.Repository.TransactionRepository;
import com.example.ebankify.Repository.UserRepository;
import com.example.ebankify.Service.TransactionService;
import org.apache.tomcat.util.bcel.Const;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final ModelMapper transMapper;
    private static double bar=1000000.0;

    public TransactionServiceImpl(TransactionRepository transactionRepo, AccountRepository accountRepo, ModelMapper transMapper , UserRepository userRepository) {
        this.transactionRepository = transactionRepo;
        this.accountRepository = accountRepo;
        this.transMapper = transMapper;
        this.userRepository=userRepository;
    }

    @Override
    public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
        Optional<Account> sourceAccountOpt = accountRepository.findById(transactionDTO.getSourceAccountId());
        Optional<Account> destinationAccountOpt = accountRepository.findById(transactionDTO.getDestinationAccountId());

        if (sourceAccountOpt.isEmpty() || destinationAccountOpt.isEmpty()) {
            throw new RuntimeException("Source or destination account not found"); // Handle with a custom exception
        } else if (!accountRepository.existsById(sourceAccountOpt.get().getId())||!accountRepository.existsById(destinationAccountOpt.get().getId())){
            throw new RuntimeException("Source or destination account not found");
        }

        Account sourceAccount = sourceAccountOpt.get();
        Account destinationAccount = destinationAccountOpt.get();

        // Validate sufficient balance for debit
        if (sourceAccount.getBalance().compareTo(transactionDTO.getAmount()) < 0) {
            throw new RuntimeException("Insufficient funds in source account"); // Handle with custom exception
        }else if(transactionDTO.getAmount()>bar){
            transactionDTO.setStatus(TransactionStatus.PENDING);
        }

        Transaction transaction=transMapper.map(transactionDTO,Transaction.class);
        Transaction savedTransaction = transactionRepository.save(transaction);
        return convertToDto(transaction);
    }

    @Override
    public TransactionDTO getTransactionById(Long transactionId) {
        return transactionRepository.findById(transactionId)
                .map(this::convertToDto)
                .orElse(null); // Handle with a custom exception if not found
    }

    @Override
    public List<TransactionDTO> getTransactionsByAccountId(Long accountId) {
        return transactionRepository.findBySourceAccountIdOrDestinationAccountId(accountId, accountId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TransactionDTO> getAllTransactions() {
        return transactionRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean cancelTransaction(Long transactionId) {
        Optional<Transaction> transactionOpt = transactionRepository.findById(transactionId);
        if (transactionOpt.isPresent()) {
            Transaction transaction = transactionOpt.get();
            transaction.setStatus(TransactionStatus.REJECTED);
            transactionRepository.save(transaction);
            return true;
        }
        return false;
    }

    @Override
    public TransactionDTO getTransactionofAccountById(Long accountId, Long id) {
        return convertToDto(transactionRepository.findBySourceAccountIdOrDestinationAccountIdAndId(accountId,accountId,id));
    }

    @Override
    public boolean acceptTransaction(Long id) {
        Optional<Transaction> transactionOpt = transactionRepository.findById(id);
        if (transactionOpt.isPresent()) {
            Transaction transaction = transactionOpt.get();
            Account sourceAccount=transaction.getSourceAccount();
            Account destinationAccount=transaction.getDestinationAccount();
            sourceAccount.setBalance(sourceAccount.getBalance() - transaction.getAmount());
            destinationAccount.setBalance(destinationAccount.getBalance() + transaction.getAmount());
            transaction.setStatus(TransactionStatus.COMPLETED);
            transactionRepository.save(transaction);
            return true;
        }
        return false;
    }

    private TransactionDTO convertToDto(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setId(transaction.getId());
        transactionDTO.setAmount(transaction.getAmount());
        transactionDTO.setType(transaction.getType());
        transactionDTO.setSourceAccountId(transaction.getSourceAccount().getId());
        transactionDTO.setDestinationAccountId(transaction.getDestinationAccount().getId());
        transactionDTO.setStatus(transaction.getStatus());
        return transactionDTO;
    }
}
