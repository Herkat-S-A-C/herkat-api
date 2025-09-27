package com.herkat.dtos.client;

import com.herkat.models.Client;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewClientDto {

    @NotBlank(message = "El nombre del cliente es obligatorio.")
    @Size(max = 50, message = "El nombre no puede superar los 50 caracteres.")
    private String name;

    @NotBlank(message = "El email del cliente es obligatorio.")
    @Email(message = "El email debe de ser correcto.")
    private String email;

    @NotNull(message = "El numero del cliente es obligatorio.")
    @Size(max = 20, message = "El numero no puede superar los 20 caracteres.")
    private String phone;

    @NotNull(message = "La direccion del cliente es obligatorio.")
    @Size(max = 100, message = "La direccion no puede superar los 50 caracteres.")
    private String address;

    public static Client toEntity(NewClientDto newClientDto) {
        return  Client.newClient(
                newClientDto.getName(),
                newClientDto.getEmail(),
                newClientDto.getPhone(),
                newClientDto.getAddress()
        );
    }
}
