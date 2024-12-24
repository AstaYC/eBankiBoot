package com.example.ebankify.Entity;
import com.example.ebankify.Entity.Enums.LoanStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "loans")
public class Loan {
    @Id
    @GeneratedValue
    private long id;
    private double principle;
    private double interest_rate;
    private LocalDate start_date;
    private int termMonths;
    private LoanStatus approved;
    private String garanty;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
