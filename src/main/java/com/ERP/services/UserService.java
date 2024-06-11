package com.ERP.services;

import com.ERP.dtos.UserDto;
import com.ERP.entities.Authentication;
import com.ERP.entities.User;
import com.ERP.exceptions.IdNotFoundException;
import com.ERP.repositories.UserEntityRepository;
import com.ERP.repositories.UserRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class UserService
{
    @Autowired
    private UserEntityRepository userEntityRepository;

    private UserRepository userRepository;
    private ObjectMapper objectMapper;

    public UserService(UserRepository userRepository, ObjectMapper objectMapper)
    {
        this.userRepository=userRepository;
        this.objectMapper=objectMapper;
    }

    public UserDto addUser(UserDto userDto) {
        try {
            User newUser = objectMapper.convertValue(userDto, User.class);
            userRepository.save(newUser);
            Authentication authenticationUser= objectMapper.convertValue(newUser,Authentication.class);
            userEntityRepository.save(authenticationUser);
            return objectMapper.convertValue(newUser, UserDto.class);
        } catch (Exception e) {
            throw new IdNotFoundException("Error adding user: " + e.getMessage());
        }
    }

    public UserDto updateUser(UserDto userDto, long userId) {
        try {
            //get the user which we need to update
            User user= userRepository.findById(userId).orElseThrow(()-> new IdNotFoundException("User not found with id: "+userId));

            // Update user fields if they are not null or empty in userDto
            if (Objects.nonNull(userDto.getUsername()) && !userDto.getUsername().isEmpty()) {
                user.setUsername(userDto.getUsername());
            }
            if (Objects.nonNull(userDto.getPassword()) && !userDto.getPassword().isEmpty()) {
                user.setPassword(userDto.getPassword());
            }
            userRepository.save(user);
            return objectMapper.convertValue(user, UserDto.class);
        }
        catch (Exception e) {
            throw new IdNotFoundException("Error updating user: " + e.getMessage());
        }
    }

    public UserDto findUser(long userId) {
        try {
            User userToSearch = userRepository.findById(userId)
                    .orElseThrow(() -> new IdNotFoundException("User not found with id: " + userId));
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return objectMapper.convertValue(userToSearch, UserDto.class);
        } catch (Exception e) {
            throw new IdNotFoundException("Error finding user: " + e.getMessage());
        }
    }

    public List<UserDto> addAllUser(List<UserDto> userDtos) {
        try {
            List<User> userList= Arrays.asList(objectMapper.convertValue(userDtos, User[].class));
            userRepository.saveAll(userList);
            return Arrays.asList(objectMapper.convertValue(userDtos, UserDto[].class));
        } catch (Exception e) {
            throw new IdNotFoundException("Error adding all users: " + e.getMessage());
        }
    }

    public List<UserDto> findAllUser() {
        try {
            List<User> userList = userRepository.findAll();
            return Arrays.asList(objectMapper.convertValue(userList, UserDto[].class));
        } catch (Exception e) {
            throw new IdNotFoundException("Error finding all users: " + e.getMessage());
        }
    }

}
