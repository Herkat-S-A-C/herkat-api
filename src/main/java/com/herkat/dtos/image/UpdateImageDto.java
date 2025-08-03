package com.herkat.dtos.image;

import com.herkat.models.Image;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class UpdateImageDto {

    private Integer id;

    private MultipartFile image;

    public static Image updateEntity(Image existing, String newUrl, String newPublicId) {
        return new Image(
                existing.getId(),
                newUrl != null ? newUrl : existing.getUrl(),
                newPublicId != null ? newPublicId : existing.getPublicId()
        );
    }

}
