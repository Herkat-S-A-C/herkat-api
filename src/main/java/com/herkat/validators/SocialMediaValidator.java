package com.herkat.validators;

import com.herkat.exceptions.ErrorMessage;
import com.herkat.exceptions.HerkatException;
import com.herkat.models.SocialMediaType;
import com.herkat.repositories.SocialMediaRepository;
import org.springframework.stereotype.Component;

@Component
public class SocialMediaValidator {

    private final SocialMediaRepository repository;

    public SocialMediaValidator(SocialMediaRepository repository) {
        this.repository = repository;
    }

    public void validateUrlOnUpdate(SocialMediaType type, String url) {
        repository.findByUrl(url)
                .ifPresent(existingSocialMedia -> {
                    if(!existingSocialMedia.getType().equals(type)) {
                        throw new HerkatException(ErrorMessage.DUPLICATE_RECORD);
                    }
                });
    }

}
