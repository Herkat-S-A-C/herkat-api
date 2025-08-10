package com.herkat.dtos.banner;

import com.herkat.models.Banner;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BannerDto {

    private Integer id;

    private String name;

    private String imageUrl;

    public static BannerDto fromEntity(Banner banner) {
        return new BannerDto(
                banner.getId(),
                banner.getName(),
                banner.getImage().getUrl()
        );
    }

}
