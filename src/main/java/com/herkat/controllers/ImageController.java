package com.herkat.controllers;

import com.herkat.dtos.image.ImageDto;
import com.herkat.models.Image;
import com.herkat.services.ImageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    private final ImageService service;

    public ImageController(ImageService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Image addImage(@RequestParam("file") MultipartFile file) throws IOException {
        return service.addImage(file);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ImageDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ImageDto findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ImageDto updateImage(@PathVariable Integer id,
                                @RequestParam("file") MultipartFile newFile) throws IOException {
        return service.update(id, newFile);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable Integer id) throws IOException {
        service.delete(id);
    }

}
