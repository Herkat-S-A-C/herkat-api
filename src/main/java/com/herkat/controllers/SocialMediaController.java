package com.herkat.controllers;

import com.herkat.dtos.social_media.SocialMediaDto;
import com.herkat.dtos.social_media.UpdateSocialMediaDto;
import com.herkat.models.SocialMediaType;
import com.herkat.services.SocialMediaService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/social-media")
public class SocialMediaController {

    private final SocialMediaService service;

    public SocialMediaController(SocialMediaService service) {
        this.service = service;
    }

    @PutMapping("/update/{type}")
    @ResponseStatus(HttpStatus.OK)
    public SocialMediaDto update(@PathVariable SocialMediaType type,
                                 @Valid @RequestBody UpdateSocialMediaDto updateSocialMediaDto) {
        return service.update(type, updateSocialMediaDto);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<SocialMediaDto> findAll() {
        return service.findAll();
    }

}
