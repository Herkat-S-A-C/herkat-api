package com.herkat.dtos.banner;

import com.herkat.models.Banner;
import com.herkat.models.Image;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateBannerDto {

    @Size(max = 50, message = "El nombre del banner no puede superar los 50 caracteres.")
    private String name;

    public static Banner updateEntity(UpdateBannerDto dto,
                                      Banner existingBanner,
                                      Image newImage) {
        return new Banner(
                existingBanner.getId(),
                dto.getName() != null ? dto.getName() : existingBanner.getName(),
                newImage != null ? newImage : existingBanner.getImage(),
                existingBanner.getRegistrationDate()
        );
    }

}
