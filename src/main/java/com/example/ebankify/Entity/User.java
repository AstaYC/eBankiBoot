package com.example.ebankify.Entity;

import com.example.ebankify.Entity.Enums.Role;
import jakarta.persistence.*;
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
    @Enumerated(EnumType.STRING) // Ensure the enum is stored as a string in the database
    @Column(name = "role")
    private Role role;
    private double monthly_income;
}
