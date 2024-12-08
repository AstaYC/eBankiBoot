package com.example.ebankify.DTO;

import com.example.ebankify.Entity.Enums.Role;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private long id;
    private String name;
    private String email;
    private String password;
    private int age;
    private int creditScore;
    private Role role;
    private Double monthly_income;

    public UserDTO(long id, String email, String password, Role role) {
    }
}
