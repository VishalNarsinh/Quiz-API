package com.quizapi.repository;

import com.quizapi.entity.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp,String> {

    Optional<Otp> findByEmail(String email);
}
