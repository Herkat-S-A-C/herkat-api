package com.herkat.controllers;

import com.herkat.dtos.image.ImageDto;
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

    @PostMapping(path = "/new", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ImageDto addImage(@RequestParam("file") MultipartFile file) throws IOException {
        return service.addImage(file);
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<ImageDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}/details")
    @ResponseStatus(HttpStatus.OK)
    public ImageDto findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PutMapping(value = "/update/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ImageDto updateImage(@PathVariable Integer id,
                                @RequestParam("file") MultipartFile newFile) throws IOException {
        return service.updateImage(id, newFile);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteImage(@PathVariable Integer id) throws IOException {
        service.delete(id);
    }

}
