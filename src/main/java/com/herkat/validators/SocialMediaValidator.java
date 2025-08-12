package com.herkat.validators;

import com.herkat.dtos.socialMedia.UpdateSocialMediaDto;
import com.herkat.exceptions.BadRequestException;
import org.springframework.stereotype.Component;

@Component
public class SocialMediaValidator {

    public void validateBeforeUpdate(UpdateSocialMediaDto dto) {
        if(dto.getUrl() == null || dto.getUrl().isBlank()) {
            throw new BadRequestException("La URL no puede estar vacía.");
        }
    }

}
