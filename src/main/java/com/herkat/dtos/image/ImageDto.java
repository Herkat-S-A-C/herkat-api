package com.herkat.dtos.image;

import com.herkat.models.Image;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImageDto {

    private Integer id;

    private String url;

    private String publicId;

    public static ImageDto fromEntity(Image image) {
        return new ImageDto(
                image.getId(),
                image.getUrl(),
                image.getPublicId()
        );
    }

}
