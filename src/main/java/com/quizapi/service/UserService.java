package com.quizapi.service;

import com.quizapi.dto.OtpDto;
import com.quizapi.dto.UserDto;
import com.quizapi.entity.User;
import com.quizapi.utils.Message;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface UserService {
    UserDto saveUser(UserDto userDto, MultipartFile file,int roleId);

    UserDto updateUser(UserDto updatedData,int userId,MultipartFile file);


    UserDto findByEmail(String email);

    void deleteUser(UserDto userDto) throws IOException;

    UserDto getUserById(int userId);

    List<UserDto> getAllUsers();

    Message verifyEmail(String email);

    UserDto userToDto(User user);

    User dtoToUser(UserDto userDto);

    boolean doesEmailExist(String email);

    void validateAndClearOtp(UserDto userDto, OtpDto otpDto);
}
