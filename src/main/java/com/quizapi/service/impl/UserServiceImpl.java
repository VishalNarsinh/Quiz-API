package com.quizapi.service.impl;

import com.quizapi.dto.OtpDto;
import com.quizapi.dto.UserDto;
import com.quizapi.entity.Otp;
import com.quizapi.entity.User;
import com.quizapi.exception.ApiException;
import com.quizapi.exception.ResourceNotFound;
import com.quizapi.repository.OtpRepository;
import com.quizapi.repository.RoleRepository;
import com.quizapi.repository.UserRepository;
import com.quizapi.service.UserService;
import com.quizapi.utils.AppConstants;
import com.quizapi.utils.Helper;
import com.quizapi.utils.ImageUploadResponse;
import com.quizapi.utils.Message;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ImageServiceImpl imageService;
    private final EmailServiceImpl emailService;
    private final OtpRepository otpRepository;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, RoleRepository roleRepository, ImageServiceImpl imageService, EmailServiceImpl emailService, OtpRepository otpRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.imageService = imageService;
        this.emailService = emailService;
        this.otpRepository = otpRepository;
    }
    public User dtoToUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    @Override
    public boolean doesEmailExist(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        return user.isPresent();
    }

    @Override
    public Message verifyEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isPresent()){
            return Message.builder().content("Email Already Exists").type("error").build();
        }else{
            Random random = new Random();
            int min = 100000;
            int max = 999999+1;
            int otp = random.nextInt(min, max);
            Otp otpOject = Otp.builder().otp(otp).email(email).build();
            Otp saveObject = otpRepository.save(otpOject);
            log.info(" OTP object : {}",saveObject);
            String body = Helper.getHtmlBodyForOtpVerification(otp);
            emailService.sendHtmlEmail(email,"OTP Verification",body);
            return Message.builder().content("OTP Sent Successfully").type("success").build();
        }
    }

    public UserDto userToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public UserDto saveUser(UserDto userDto, MultipartFile file, int roleId) {
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(roleRepository.findById(roleId).get());
        if(file!=null && !file.isEmpty()) {
            try {
                ImageUploadResponse imageUploadResponse = imageService.uploadImage(file, AppConstants.USER_FOLDER);
                user.setUserImageUrl(imageUploadResponse.getUrl());
                user.setUserImagePublicId(imageUploadResponse.getPublicId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            user.setUserImageUrl(AppConstants.DEFAULT_USER_IMAGE_URL);
            user.setUserImagePublicId(AppConstants.DEFAULT_USER_IMAGE_PUBLIC_ID);
        }
        user.setEnabled(true);
        return userToDto(userRepository.save(user));
    }



    @Override
    public UserDto findByEmail(String email) {
        return userToDto(userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFound("User", "email", email)));
    }

    @Override
    public UserDto updateUser(UserDto updatedData,int userId, MultipartFile file) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "id", userId));
        User upDatedUser = modelMapper.map(updatedData, User.class);
        user.setName(upDatedUser.getName());
        user.setPhone(upDatedUser.getPhone());
        if(file!=null && !file.isEmpty()) {
            try {
                imageService.deleteImage(user.getUserImagePublicId());
                ImageUploadResponse imageUploadResponse = imageService.uploadImage(file, AppConstants.USER_FOLDER);
                user.setUserImageUrl(imageUploadResponse.getUrl());
                user.setUserImagePublicId(imageUploadResponse.getPublicId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return userToDto(userRepository.save(user));
    }

    @Override
    public void deleteUser(UserDto userDto)  {
        User user = userRepository.findById(userDto.getUserId()).orElseThrow(() -> new ResourceNotFound("User", "id", userDto.getUserId()));
        try {
            imageService.deleteImage(user.getUserImagePublicId());
        }catch (Exception e){
            throw new RuntimeException(e);
        }
        userRepository.delete(user);
    }

    @Override
    public UserDto getUserById(int userId) {
        return userToDto(userRepository.findById(userId).orElseThrow(() -> new ResourceNotFound("User", "id", userId)));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream().map(this::userToDto).collect(Collectors.toList());
    }

    @Override
    public void validateAndClearOtp(UserDto userDto, OtpDto otpDto) {
        Otp otpDB = otpRepository.findByEmail(userDto.getEmail()).orElseThrow(() -> new ApiException("OTP expired or not set. Please verify your email again."));
        if (otpDB.getOtp() != otpDto.getOtp()) {
            throw new ApiException("Invalid OTP");
        }
        otpRepository.delete(otpDB);
    }
}
