package com.herkat.dtos.image;

import com.herkat.models.Image;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class NewImageDto {

    @NotNull(message = "La imagen es obligatoria.")
    private MultipartFile image;

    public static Image toEntity(String url, String s3Key) {
        return Image.newImage(
                url,
                s3Key);
    }

}
