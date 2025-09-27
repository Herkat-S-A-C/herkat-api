package com.herkat.services;

import com.herkat.dtos.client.ClientDto;
import com.herkat.dtos.client.NewClientDto;
import com.herkat.dtos.client.UpdateClientDto;
import com.herkat.exceptions.ErrorMessage;
import com.herkat.exceptions.HerkatException;
import com.herkat.models.Client;
import com.herkat.repositories.ClientRepository;
import com.herkat.validators.ClientValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientValidator validator;

    public ClientService(ClientRepository clientRepository,
                          ClientValidator validator) {
        this.clientRepository = clientRepository;
        this.validator = validator;
    }

    @Transactional
    public ClientDto register(NewClientDto newClientDto){
        // Validamos las reglas de negocio antes de registrar
        validator.validateEmailUniqueness(newClientDto.getEmail());

        // Convertimos el DTO del cliente a entidad
        Client newClient = NewClientDto.toEntity(newClientDto);

        // Guardamos la entidad en la DB
        Client savedClient = clientRepository.save(newClient);

        return ClientDto.fromEntity(savedClient);
    }

    @Transactional(readOnly = true)
    public List<ClientDto> findAll() {
        // Buscamos todos los clientes
        return clientRepository.findAll()
                .stream()
                .map(ClientDto::fromEntity)
                .toList();
    }

    @Transactional(readOnly = true)
    public ClientDto findById(Integer id) {
        // Buscamos el cliente por su ID
        return clientRepository.findById(id)
                .map(ClientDto::fromEntity)
                .orElseThrow(() -> new HerkatException(ErrorMessage.CLIENT_NOT_FOUND));
    }

    @Transactional
    public ClientDto update(Integer id, UpdateClientDto updateClientDto){
        // Validamos las reglas de negocio antes de actualizar
        validator.validateEmailOnUpdate(id, updateClientDto.getEmail());

        // Buscamos el cliente por su ID
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new HerkatException(ErrorMessage.CLIENT_NOT_FOUND));

        // Creamos la entidad con los datos actualizados
        Client updatedClient = UpdateClientDto.updateEntity(
                updateClientDto,
                existingClient
        );

        // Guardamos la entidad actualizada en la DB
        Client savedClient = clientRepository.save(updatedClient);

        // Convertimos la entidad a DTO para retornarlo
        return ClientDto.fromEntity(savedClient);
    }

    @Transactional
    public void delete(Integer id){
        // Buscamos el cliente por su ID
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new HerkatException(ErrorMessage.CLIENT_NOT_FOUND));

        // Eliminamos el cliente de la DB
        clientRepository.delete(existingClient);
    }

}

