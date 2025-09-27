package com.herkat.dtos.client;

import com.herkat.models.Client;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateClientDto {

    @Size(max = 50, message = "El nombre del cliente no puede superar los 50 caracteres.")
    private String name;

    private String email;

    private String phone;

    private String address;

    public static Client updateEntity(UpdateClientDto dto,
                                      Client existingClient) {
        return new Client(
                existingClient.getId(),
                dto.getName() != null && !dto.getName().isEmpty() ? dto.getName() : existingClient.getName(),
                dto.getEmail() != null && !dto.getEmail().isEmpty() ? dto.getEmail() : existingClient.getEmail(),
                dto.getPhone() != null && !dto.getPhone().isEmpty() ? dto.getPhone() : existingClient.getPhone(),
                dto.getAddress() != null && !dto.getAddress().isEmpty() ? dto.getAddress() : existingClient.getAddress(),
                existingClient.getCreatedAt()
        );
    }

}
