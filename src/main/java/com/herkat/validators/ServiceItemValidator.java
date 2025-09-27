package com.herkat.validators;

import com.herkat.exceptions.ErrorMessage;
import com.herkat.exceptions.HerkatException;
import com.herkat.repositories.ServiceItemRepository;
import org.springframework.stereotype.Component;

@Component
public class ServiceItemValidator {

    private final ServiceItemRepository repository;

    public ServiceItemValidator(ServiceItemRepository repository) {
        this.repository = repository;
    }

    public void validateNameUniqueness(String name) {
        if(repository.findByNameIgnoreCase(name).isPresent()) {
            throw new HerkatException(ErrorMessage.DUPLICATE_RECORD);
        }
    }

    public void validateNameOnUpdate(Integer serviceItemId, String name) {
        repository.findByNameIgnoreCase(name)
                .ifPresent(existingServiceItem -> {
                    if(existingServiceItem.getId().equals(serviceItemId)) {
                        throw new HerkatException(ErrorMessage.DUPLICATE_RECORD);
                    }
                });
    }

}
