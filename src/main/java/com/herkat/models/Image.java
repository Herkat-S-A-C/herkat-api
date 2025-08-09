package com.herkat.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String url;

    @Column(name = "public_id", unique = true, nullable = false)
    private String publicId;

    public static Image newImage(String url, String publicId) {
        return new Image(
                null,
                url,
                publicId);
    }

}
