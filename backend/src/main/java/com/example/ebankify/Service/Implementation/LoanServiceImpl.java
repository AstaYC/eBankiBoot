package com.example.ebankify.Service.Implementation;

import com.example.ebankify.DTO.LoanDTO;
import com.example.ebankify.Entity.Enums.LoanStatus;
import com.example.ebankify.Entity.Enums.Role;
import com.example.ebankify.Entity.Loan;
import com.example.ebankify.Entity.User;
import com.example.ebankify.Repository.LoanRepository;
import com.example.ebankify.Repository.UserRepository;
import com.example.ebankify.Service.LoanService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final ModelMapper LoanMapper;

    public LoanServiceImpl(LoanRepository loanRepository, UserRepository userRepository,ModelMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.LoanMapper=loanMapper;
    }

    @Override
    @Transactional
    public LoanDTO createLoan(LoanDTO loanDTO) {
        Optional<User> user = userRepository.findById(loanDTO.getUserId());

        if (user.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        loanDTO.setStart_date(LocalDate.now());
        Double interest=loanDTO.getInterest_rate();
        loanDTO.setInterest_rate(interest*0.01);

        double test=(loanDTO.getPrinciple()/loanDTO.getTermMonths())+(loanDTO.getPrinciple()*(loanDTO.getInterest_rate()/100))+1000;

        List<Loan> loans = loanRepository.findByUserId(user.get().getId());
        List<Loan> loansNotFinished = loans.stream()
                .filter(e -> e.getStart_date().plusMonths(e.getTermMonths()).isAfter(LocalDate.now()))
                .toList();
        if (user.get().getRole().equals(Role.ADMIN)){
            throw new RuntimeException("your an admin you have all the money why do you need a loan from yourself");
        }else if (user.get().getAge()<18){
            throw new RuntimeException("you are underage for this operation");
        }else if (user.get().getMonthly_income()<test){
            throw new RuntimeException("you dont have the right amount of monthly wage to cover this loan ");
        } else if (!loans.isEmpty() && loansNotFinished.size()>=2) {
            throw new RuntimeException("you are behind a couple loans");
        }else if(isDebtRatioWithinLimit(user.get())){
            throw new RuntimeException("you are not eligible for the loan");
        }
        loanDTO.setApproved(LoanStatus.PENDING);

        Loan loan = LoanMapper.map(loanDTO,Loan.class);
        Loan savedLoan = loanRepository.save(loan);
        return loanDTO;
    }

    @Override
    @Transactional
    public LoanDTO approveLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found")); // Handle with custom exception if needed


        loan.setApproved(LoanStatus.APPROVED);
        Loan updatedLoan = loanRepository.save(loan);

        return convertToDto(updatedLoan);
    }

    @Override
    @Transactional
    public LoanDTO rejectLoan(Long loanId) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found")); // Handle with custom exception if needed

        loan.setApproved(LoanStatus.DECLINED);
        Loan updatedLoan = loanRepository.save(loan);

        return convertToDto(updatedLoan);
    }

    @Override
    public LoanDTO getLoanById(Long loanId) {
        return loanRepository.findById(loanId)
                .map(this::convertToDto)
                .orElse(null); // Handle with custom exception if loan not found
    }

    @Override
    public List<LoanDTO> getLoansByUserId(Long userId) {
        return loanRepository.findByUserId(userId)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LoanDTO> getAllLoans() {
        return loanRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public boolean isDebtRatioWithinLimit(User user) {

        double monthlyIncome = user.getMonthly_income();

        double totalDebt = loanRepository.findByUserId(user.getId())
                .stream()
                .mapToDouble(Loan::getPrinciple)
                .sum();

        double debtRatio = totalDebt / monthlyIncome;

        // Vérification du seuil
        return debtRatio > 0.40;
    }

    // Helper method to convert Loan entity to LoanDTO
    private LoanDTO convertToDto(Loan loan) {
        LoanDTO loanDTO = new LoanDTO();
        loanDTO.setId(loan.getId());
        loanDTO.setPrinciple(loan.getPrinciple());
        loanDTO.setInterest_rate(loan.getInterest_rate());
        loanDTO.setTermMonths(loan.getTermMonths());
        loanDTO.setApproved(loan.getApproved());
        loanDTO.setUserId(loan.getUser().getId());
        loanDTO.setStart_date(loan.getStart_date());
        return loanDTO;
    }
}
