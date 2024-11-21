package com.quizapi.utils;

public class Helper {
    public static String getHtmlBodyForOtpVerification(int otp) {
        return String.format(
                "<!DOCTYPE html><html><head><title>OTP Verification</title><style>body {font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;}.container {width: 100%%; max-width: 600px; margin: 0 auto; background-color: #ffffff; padding: 20px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);}.header {text-align: center; padding: 20px 0;}.header img {width: 100px;}.content {margin-top: 20px;}.content h1 {color: #333333;}.content p {color: #666666; line-height: 1.6;}.otp {font-size: 24px; font-weight: bold; color: #007BFF;}.footer {text-align: center; margin-top: 20px; padding: 20px 0; border-top: 1px solid #eeeeee; color: #999999;}</style></head><body><div class=\"container\"><div class=\"header\"><img src=\"https://res.cloudinary.com/dzew8rxxw/image/upload/v1722927658/scm/verifyemail_svnt20.png\" alt=\"QuizHub\"></div><div class=\"content\"><h1>OTP Verification</h1><p>Dear User,</p><p>Your OTP for verifying your email address on QuizHub is:</p><p class=\"otp\">%d</p><p>Please enter this code in the app to complete your verification. This OTP is valid for a limited time.</p><p>If you did not request an OTP, please ignore this email.</p><p>Thank you,<br>QuizHub Team</p></div><div class=\"footer\">&copy; 2024 QuizHub. All rights reserved.</div></div></body></html>",
                otp
        );
    }

}
