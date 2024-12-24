package com.example.ebankify.Entity;
import com.example.ebankify.Entity.Enums.TransactionStatus;
import com.example.ebankify.Entity.Enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue
    private long id;
    private TransactionType type;
    private TransactionStatus status;
    private Double amount;
    @ManyToOne
    @JoinColumn(name = "source_account_id")
    private Account sourceAccount;
    @ManyToOne
    @JoinColumn(name = "destination_account_id")
    private Account destinationAccount;
}
