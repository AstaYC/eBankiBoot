package com.example.ebankify.DTO;

import com.example.ebankify.Entity.Enums.Role;
import lombok.*;

@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO {
    private Role role;
}
