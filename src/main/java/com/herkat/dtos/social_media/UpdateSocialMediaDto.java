package com.herkat.dtos.social_media;

import com.herkat.models.SocialMedia;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSocialMediaDto {

    @NotBlank(message = "La URL es obligatoria.")
    private String url;

    public static SocialMedia updateEntity(UpdateSocialMediaDto dto, SocialMedia existing) {
        return new SocialMedia(
                existing.getId(),
                dto.getUrl() != null && !dto.getUrl().isEmpty() ? dto.getUrl() : existing.getUrl(),
                existing.getType()
        );
    }

}
