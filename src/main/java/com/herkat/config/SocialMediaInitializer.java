package com.herkat.config;

import com.herkat.models.SocialMedia;
import com.herkat.models.SocialMediaType;
import com.herkat.repositories.SocialMediaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SocialMediaInitializer implements CommandLineRunner {

    private final SocialMediaRepository repository;

    public SocialMediaInitializer(SocialMediaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void run(String... args) {
        for(SocialMediaType type : SocialMediaType.values()) {
            repository.findByType(type)
                    .orElseGet(() -> repository.save(SocialMedia.newSocialMedia("", type)));
        }
    }

}
