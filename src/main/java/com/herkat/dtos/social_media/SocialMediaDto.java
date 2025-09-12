package com.herkat.dtos.social_media;

import com.herkat.models.SocialMedia;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SocialMediaDto {

    private Integer id;

    private String url;

    private String type;

    public static SocialMediaDto fromEntity(SocialMedia socialMedia) {
        return new SocialMediaDto(
                socialMedia.getId(),
                socialMedia.getUrl(),
                socialMedia.getType().name()
        );
    }

}
