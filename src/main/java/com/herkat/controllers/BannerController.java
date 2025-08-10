package com.herkat.controllers;

import com.herkat.dtos.banner.BannerDto;
import com.herkat.dtos.banner.NewBannerDto;
import com.herkat.dtos.banner.UpdateBannerDto;
import com.herkat.services.BannerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/banners")
public class BannerController {

    private final BannerService service;

    public BannerController(BannerService service) {
        this.service = service;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public BannerDto register(@ModelAttribute @Valid NewBannerDto newBannerDto,
                              @RequestPart("image") MultipartFile image) throws IOException {
        return service.register(newBannerDto, image);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BannerDto> findAll() {
        return service.findAll();
    }

    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public BannerDto findById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @GetMapping("/name/{name}")
    @ResponseStatus(HttpStatus.OK)
    public BannerDto findByName(@PathVariable String name) {
        return service.findByName(name);
    }

    @PutMapping(path = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public BannerDto update(@PathVariable Integer id,
                             @ModelAttribute @Valid UpdateBannerDto updateBannerDto,
                             @RequestPart(value = "image", required = false) MultipartFile image) throws IOException {
        return service.update(id, updateBannerDto, image);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) throws IOException {
        service.delete(id);
    }

}
