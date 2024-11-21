package com.quizapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quizapi.exception.ResourceNotFound;
import com.quizapi.payloads.JwtRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import  com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/test")
public class TestController {

    private final ObjectMapper jacksonObjectMapper;
    private final Logger logger = LoggerFactory.getLogger(TestController.class);
    public TestController(ObjectMapper jacksonObjectMapper) {
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    @PostMapping(value = "/testfile", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> testRequestPart(
            @RequestPart("file") MultipartFile file,
            @RequestPart("jwtRequest") String data) {

        System.out.println(file);
        System.out.println(file.getOriginalFilename());
        JwtRequest jwtRequest = null;
//        map.forEach((key, value) -> System.out.println("Key : " + key + " Value : " + value));
//        System.out.println(map);
        try {
            jwtRequest = jacksonObjectMapper.readValue(data, JwtRequest.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        logger.info("{}",jwtRequest);
//        System.out.println(jwtRequest);
        return ResponseEntity.ok("File and JSON data received successfully");
    }

    @GetMapping("/csrf-check")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("File and JSON data received successfully");
    }

    @GetMapping("/error")
    public ResponseEntity<?> testError(HttpSession httpSession){
        logger.info("OTP controller side on testing : {}",httpSession.getAttribute("otp"));
        throw new ResourceNotFound("User","id",1);
//        return ResponseEntity.ok("File and JSON data received successfully");
    }
}
