package com.quizapi.utils;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ImageUploadResponse {
    private String url;
    private String publicId;
}
