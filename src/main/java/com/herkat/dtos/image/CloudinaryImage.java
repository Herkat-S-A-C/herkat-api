package com.herkat.dtos.image;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CloudinaryImage {

    private String url;

    private String publicId;

    public static CloudinaryImage newCloudinaryImage(String url, String publicId) {
        return new CloudinaryImage(
                url,
                publicId);
    }

}
