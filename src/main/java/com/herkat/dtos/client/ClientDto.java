package com.herkat.dtos.client;

import com.herkat.models.Client;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ClientDto {

    private Integer id;

    private String name;

    private String email;

    private String phone;

    private String address;

    public static ClientDto fromEntity(Client client) {
        return new ClientDto(
                client.getId(),
                client.getName(),
                client.getEmail(),
                client.getPhone(),
                client.getAddress()
        );
    }

}
