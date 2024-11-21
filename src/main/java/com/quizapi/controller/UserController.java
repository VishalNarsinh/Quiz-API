package com.quizapi.controller;

import com.quizapi.dto.OtpDto;
import com.quizapi.dto.UserDto;
import com.quizapi.exception.ApiException;
import com.quizapi.service.UserService;
import com.quizapi.service.impl.UserServiceImpl;
import com.quizapi.utils.AppConstants;
import com.quizapi.utils.Message;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user")
public class UserController {


    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Message> deleteUserById(@PathVariable Long userId, Authentication authentication)  {
        UserDto userDto = userService.findByEmail(authentication.getName());
        if (userDto.getUserId() != userId) {
            throw new ApiException("You are not authorized to delete this user.");
        } else {
            try {
                userService.deleteUser(userDto);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.ok(Message.builder().type("success").content("User deleted successfully.").build());

    }

    @PutMapping(value = "/update",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserDto> updateUser(
            @Valid @RequestPart("userDto") UserDto userDto,
            @RequestPart(value = "userImage",required = false) MultipartFile file) {
        return ResponseEntity.ok(userService.saveUser(userDto,file, AppConstants.NORMAL_USER));
    }
}


