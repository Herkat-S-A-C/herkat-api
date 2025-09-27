package com.herkat.validators;

import com.herkat.exceptions.ErrorMessage;
import com.herkat.exceptions.HerkatException;
import com.herkat.repositories.BannerRepository;
import org.springframework.stereotype.Component;

@Component
public class BannerValidator {

    private final BannerRepository repository;

    public BannerValidator(BannerRepository repository) {
        this.repository = repository;
    }

    public void validateNameUniqueness(String name) {
        if(repository.findByNameIgnoreCase(name).isPresent()) {
            throw new HerkatException(ErrorMessage.DUPLICATE_RECORD);
        }
    }

    public void validateNameOnUpdate(Integer bannerId, String newName) {
        repository.findByNameIgnoreCase(newName)
                .ifPresent(existingType -> {
                    if(!existingType.getId().equals(bannerId)) {
                        throw new HerkatException(ErrorMessage.DUPLICATE_RECORD);
                    }
                });
    }

}
