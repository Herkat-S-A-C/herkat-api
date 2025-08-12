package com.herkat.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "social_media")
public class SocialMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String url;

    @Enumerated(EnumType.STRING)
    @Column(unique = true)
    private SocialMediaType type;

    public static SocialMedia newSocialMedia(String url, SocialMediaType type) {
        return new SocialMedia(
                null,
                url,
                type
        );
    }

}
