package com.herkat.validators;

import com.herkat.exceptions.ErrorMessage;
import com.herkat.exceptions.HerkatException;
import com.herkat.repositories.ProductRepository;
import org.springframework.stereotype.Component;

@Component
public class ProductValidator {

    private final ProductRepository repository;

    public ProductValidator(ProductRepository repository) {
        this.repository = repository;
    }

    public void validateNameUniqueness(String name) {
        if(repository.findByNameIgnoreCase(name).isPresent()) {
            throw new HerkatException(ErrorMessage.DUPLICATE_RECORD);
        }
    }

    public void validateNameOnUpdate(Integer id, String name) {
        repository.findByNameIgnoreCase(name)
                .ifPresent(existingProduct -> {
                    if(!existingProduct.getId().equals(id)) {
                        throw new HerkatException(ErrorMessage.DUPLICATE_RECORD);
                    }
                });
    }

}
