package com.example.ebankify.Service;

import com.example.ebankify.DTO.LoanDTO;

import java.util.List;

public interface LoanService {

    LoanDTO createLoan(LoanDTO loanDTO);

    LoanDTO approveLoan(Long loanId);

    LoanDTO rejectLoan(Long loanId);

    LoanDTO getLoanById(Long loanId);

    List<LoanDTO> getLoansByUserId(Long userId);

    List<LoanDTO> getAllLoans();
}

