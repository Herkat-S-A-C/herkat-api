package com.herkat.dtos.image;

import com.herkat.models.Image;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateImageDto {

    private MultipartFile image;

    public static Image updateEntity(Image existingImage, String newUrl, String newPublicId) {
        return new Image(
                existingImage.getId(),
                newUrl != null ? newUrl : existingImage.getUrl(),
                newPublicId != null ? newPublicId : existingImage.getPublicId()
        );
    }

}
