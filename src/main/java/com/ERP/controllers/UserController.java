package com.ERP.controllers;

import com.ERP.dtos.UserDto;
import com.ERP.entities.User;
import com.ERP.exceptions.IdNotFoundException;
import com.ERP.services.UserService;
import com.ERP.utils.MyResponseGenerator;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/user")
@Validated
public class UserController
{
    UserService userService;
    private final Validator validator;

    public UserController(UserService userService, LocalValidatorFactoryBean validatorFactory)
    {
        this.userService=userService;
        this.validator = validatorFactory.getValidator();
    }

    @PostMapping("/add")
    public ResponseEntity<Object> addUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto newUser=userService.addUser(userDto);
        if(userDto!=null)
        {
            return MyResponseGenerator.generateResponse(HttpStatus.CREATED,true,"User is added",newUser);
        }
        else
        {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST,false,"Something went wrong",newUser);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @PutMapping("/update/{userId}")
    public ResponseEntity<Object> updateUser( @Valid @RequestBody UserDto userDto,@PathVariable Long userId)
    {
        UserDto userDto1= userService.updateUser(userDto,userId);
        if(userDto1!=null)
        {
            return MyResponseGenerator.generateResponse(HttpStatus.OK,true,"User is updated successfully", userDto1);
        }
        else {
            return MyResponseGenerator.generateResponse(HttpStatus.BAD_REQUEST,false,"Something went wrong and User is not updated successfully",userDto1);
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/find/{userId}")
    public ResponseEntity<Object> findUser(@PathVariable long userId)
    {
        UserDto userTofind =userService.findUser(userId);
        if(userTofind!=null)
        {
            return MyResponseGenerator.generateResponse(HttpStatus.OK,true,"User is found", userTofind);
        }
        else {
            return MyResponseGenerator.generateResponse(HttpStatus.NOT_FOUND,false,"User is not founc",userTofind);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addAll")
    public List<UserDto> addAll(@Valid @RequestBody List< UserDto> userDtos)
    {
        return userService.addAllUser(userDtos);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/findAll")
    public List<UserDto> findAll()
    {
        return userService.findAllUser();
    }

}
