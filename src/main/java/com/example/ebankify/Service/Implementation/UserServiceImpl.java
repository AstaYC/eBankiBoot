package com.example.ebankify.Service.Implementation;

import com.example.ebankify.DTO.UserDTO;
import com.example.ebankify.Entity.User;
import com.example.ebankify.Exception.NotFoundException;
import com.example.ebankify.Repository.UserRepository;
import com.example.ebankify.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, ModelMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user->userMapper.map(user,UserDTO.class)).collect(Collectors.toList());
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        return userMapper.map(user,UserDTO.class);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {

        User user = userMapper.map(userDTO,User.class);

        User savedUser = userRepository.save(user);
        return userMapper.map(savedUser,UserDTO.class);
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        userDTO.setId(userId);
        User updatedUser = userRepository.save(userMapper.map(userDTO,User.class));

        return userMapper.map(updatedUser,UserDTO.class);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        userRepository.deleteById(userId);
    }
    @Override
    public boolean existsById(Long userId){
        boolean userExist= userRepository.existsById(userId);
        if(userExist){
            return true;
        }else{
            return false;
        }
    }
    @Override
    public boolean isUserAdmin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));
        return "ADMIN".equalsIgnoreCase(user.getRole().toString());

    }
    @Override
    public boolean isUserEmployee(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with ID: " + userId));
        return "EMPLOYEE".equalsIgnoreCase(user.getRole().toString());

    }
}
