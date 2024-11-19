package com.example.ebankify.Entity;

import com.example.ebankify.Entity.Enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private long id;
    private String name;
    private String email;
    private String password;
    private int age;
    private int creditScore;
    private Role role;
    private double monthly_income;
}
