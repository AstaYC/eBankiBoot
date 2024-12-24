package com.example.ebankify.Entity;
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
@Table (name = "invoices")
public class Invoice {
    @Id
    @GeneratedValue
    private long id;
    private double amountDue;
    private LocalDate dueDate;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
