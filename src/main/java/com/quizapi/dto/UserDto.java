package com.quizapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class UserDto {
    private int userId;
    @NotBlank
    private String name;
    @NotBlank
    @Pattern(
            regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)[a-zA-Z0-9\\W]{8,20}$",
            message = "Password must be 8-20 characters, contain a digit, uppercase, lowercase, and special character"
    )
    private String password;
    @NotBlank
    @Email
    private String email;
    private String phone;
    private String userImageUrl;
    private String userImagePublicId;
    private boolean enabled;
    private Set<RoleDto> roles = new HashSet<>();
}
