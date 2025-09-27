package com.herkat.validators;

import com.herkat.exceptions.ErrorMessage;
import com.herkat.exceptions.HerkatException;
import com.herkat.repositories.ClientRepository;
import org.springframework.stereotype.Component;

@Component
public class ClientValidator {

    private final ClientRepository repository;

    public ClientValidator(ClientRepository repository) {
        this.repository = repository;
    }

    public void validateEmailUniqueness(String email) {
        if(repository.findByEmailIgnoreCase(email).isPresent()) {
            throw new HerkatException(ErrorMessage.DUPLICATE_RECORD);
        }
    }

    public void validateEmailOnUpdate(Integer clientId, String newEmail) {
        repository.findByEmailIgnoreCase(newEmail)
                .ifPresent(existingClient -> {
                    if(!existingClient.getId().equals(clientId)) {
                        throw new HerkatException(ErrorMessage.DUPLICATE_RECORD);
                    }
                });
    }

}
