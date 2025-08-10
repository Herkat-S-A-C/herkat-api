package com.herkat.dtos.banner;

import com.herkat.models.Banner;
import com.herkat.models.Image;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewBannerDto {

    @NotBlank(message = "El nombre del banner es obligatorio.")
    @Size(max = 50, message = "El nombre del banner no puede superar los 50 caracteres.")
    private String name;

    public static Banner toEntity(NewBannerDto newBannerDto, Image image) {
        return Banner.newBanner(
                newBannerDto.getName(),
                image,
                null
        );
    }

}
