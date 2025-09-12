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

    @Column(name = "s3_key", unique = true, nullable = false)
    private String s3Key;

    public static Image newImage(String url, String s3Key) {
        return new Image(
                null,
                url,
                s3Key);
    }

}
