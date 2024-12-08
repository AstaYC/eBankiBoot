package com.example.ebankify.Service;

import com.example.ebankify.DTO.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();
    UserDTO getUserById(Long userId);
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(Long userId, UserDTO userDTO);
    void deleteUser(Long userId);
    boolean existsById(Long userId);
    boolean isUserAdmin(Long userId);
    boolean isUserEmployee(Long userId);
    UserDTO getUserByEmail(String email);
}
