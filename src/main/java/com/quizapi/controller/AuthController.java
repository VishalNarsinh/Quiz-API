package com.quizapi.controller;

import com.quizapi.dto.OtpDto;
import com.quizapi.dto.UserDto;
import com.quizapi.exception.ApiException;
import com.quizapi.payloads.JwtRequest;
import com.quizapi.payloads.JwtResponse;
import com.quizapi.repository.OtpRepository;
import com.quizapi.security.jwt.JwtTokenHelper;
import com.quizapi.service.UserService;
import com.quizapi.utils.AppConstants;
import com.quizapi.utils.Message;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserDetailsService userDetailsService;
    private final JwtTokenHelper jwtTokenHelper;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthenticationManager authenticationManager;
    private final OtpRepository otpRepository;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsService customUserDetailsService, JwtTokenHelper jwtTokenHelper, ModelMapper modelMapper, UserService userService,OtpRepository otpRepository) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = customUserDetailsService;
        this.jwtTokenHelper = jwtTokenHelper;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.otpRepository = otpRepository;
    }

    private void doAuthenticate(String email, String password) {
//        jacksonObjectMapper
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            logger.info("{}",e.getLocalizedMessage());
            throw new ApiException(" Invalid Username or Password !!");
        }

    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest){
        this.doAuthenticate(jwtRequest.getEmail(), jwtRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(jwtRequest.getEmail());
        String token = jwtTokenHelper.generateToken(userDetails);
        JwtResponse jwtResponse = JwtResponse.builder().jwtToken(token).user(modelMapper.map(userDetails, UserDto.class)).build();
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    @PostMapping("/email-exist")
    public ResponseEntity<Boolean> doesEmailExist(@RequestParam("email") String email){
        boolean emailExists = userService.doesEmailExist(email);
        logger.info("{}",emailExists);
        return new ResponseEntity<>(emailExists, HttpStatus.OK);
    }

    @PostMapping("/verify-email")
    public ResponseEntity<Message> verifyEmail(@RequestParam("email") String email, HttpSession httpSession){
        if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) { // simple email regex
            throw new ApiException("Invalid email format");
        }
        Message message = userService.verifyEmail(email);
        if(message.getType().equals("error")) throw new ApiException(message.getContent());
        return new ResponseEntity<>(message, HttpStatus.OK);
    }



    @PostMapping(value = "/register",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserDto> createUser(
            @Valid @RequestPart("userDto") UserDto userDto,
            @RequestPart("otpDto") OtpDto otpDto,
            @RequestPart(value = "userImage",required = false) MultipartFile file
            ) {
        userService.validateAndClearOtp(userDto, otpDto);
        return ResponseEntity.ok(userService.saveUser(userDto,file,AppConstants.NORMAL_USER));
    }

    @PostMapping(value = "/register-admin",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UserDto> createAdmin(
            @Valid @RequestPart("userDto") UserDto userDto,
            @RequestPart("otpDto") OtpDto otpDto,
            @RequestPart(value = "userImage",required = false) MultipartFile file,
            HttpSession httpSession) {
        userService.validateAndClearOtp(userDto, otpDto);
        return ResponseEntity.ok(userService.saveUser(userDto,file,AppConstants.ADMIN_USER));
    }

}
