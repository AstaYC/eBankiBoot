package com.example.ebankify.Entity;
import com.example.ebankify.Entity.Enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue
    private long id;
    private Double balance;
    private AccountStatus status;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
