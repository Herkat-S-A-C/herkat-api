package com.herkat.validators;

import com.herkat.exceptions.ErrorMessage;
import com.herkat.exceptions.HerkatException;
import com.herkat.repositories.MachineTypeRepository;
import org.springframework.stereotype.Component;

@Component
public class MachineTypeValidator {

    private final MachineTypeRepository repository;

    public MachineTypeValidator(MachineTypeRepository repository) {
        this.repository = repository;
    }

    public void validateNameUniqueness(String name) {
        if(repository.findByNameIgnoreCase(name).isPresent()) {
            throw new HerkatException(ErrorMessage.DUPLICATE_RECORD);
        }
    }

    public void validateNameOnUpdate(Integer typeId, String name) {
        repository.findByNameIgnoreCase(name)
                .ifPresent(existingType -> {
                    if(!existingType.getId().equals(typeId)) {
                        throw new HerkatException(ErrorMessage.DUPLICATE_RECORD);
                    }
                });
    }

}
