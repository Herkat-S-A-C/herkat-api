package com.herkat.dtos.image;

import com.herkat.models.Image;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateImageDto {

    private MultipartFile image;

    public static Image updateEntity(Image existingImage, String newUrl, String newS3Key) {
        return new Image(
                existingImage.getId(),
                newUrl != null && !newUrl.isEmpty() ? newUrl : existingImage.getUrl(),
                newS3Key != null && !newS3Key.isEmpty() ? newS3Key : existingImage.getS3Key()
        );
    }

}
