package com.example.ebankify.Controller.vm.User.Request;

import com.example.ebankify.DTO.UserDTO;

public class CreateUserRequest {
    private UserDTO userDTO;
    private String role;

    public UserDTO getUserDTO() {
        return userDTO;
    }

    public void setUserDTO(UserDTO userDTO) {
        this.userDTO = userDTO;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
