package com.herkat.validators;

import com.herkat.dtos.client.NewClientDto;
import com.herkat.dtos.client.UpdateClientDto;
import com.herkat.exceptions.BadRequestException;
import com.herkat.exceptions.ConflictException;
import com.herkat.repositories.ClientRepository;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ClientValidator {

    private final ClientRepository repository;
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public ClientValidator(ClientRepository repository) {
        this.repository = repository;
    }

    public void validateBeforeRegister(NewClientDto dto) {
        if(dto.getName() == null || dto.getName().isBlank()) {
            throw new BadRequestException("El nombre del cliente no puede estar vacío.");
        }

        if(dto.getEmail() == null|| dto.getEmail().isBlank())  {
            throw new BadRequestException("El email del cliente no puede estar vacío.");
        }

        if(EMAIL_PATTERN.matcher(dto.getEmail()).matches()) {
            throw new BadRequestException("El formato del email no es válido.");
        }

        if(repository.findByEmailIgnoreCase(dto.getEmail()).isPresent()) {
            throw new ConflictException("El email ya esta registrado.");
        }
    }

    public void validateBeforeUpdate(Integer id, UpdateClientDto dto) {

        if(EMAIL_PATTERN.matcher(dto.getEmail()).matches()) {
            throw new BadRequestException("El formato del email no es válido.");
        }

        repository.findByEmailIgnoreCase(dto.getEmail())
                .ifPresent(existingClient -> {
                    if(!existingClient.getId().equals(id)) {
                        throw new ConflictException("El email ya esta registrado.");
                    }
                });
    }

}
