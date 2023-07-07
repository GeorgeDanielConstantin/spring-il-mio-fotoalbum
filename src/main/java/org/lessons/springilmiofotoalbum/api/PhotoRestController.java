package org.lessons.springilmiofotoalbum.api;


import org.lessons.springilmiofotoalbum.model.Photo;
import org.lessons.springilmiofotoalbum.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("api/v1/photo")
public class PhotoRestController {

    @Autowired
    PhotoService photoService;

    @GetMapping
    public Page<Photo> page(
            @RequestParam Optional<String> keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return photoService.getAll(keyword, pageable);
    }

    @GetMapping("/{id}")
    public Photo get(@PathVariable Integer id) {
        try {
            return photoService.getbyId(id);
        } catch (RuntimeException e) {
            throw new ResponseStatusException((HttpStatus.NOT_FOUND));
        }
    }


//    @PostMapping
//    public Photo create(@Valid @RequestBody Photo photo) {
//        try {
//            return photoService.create(photo);
//        } catch (RuntimeException e) {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public void deleteById(@PathVariable Integer id) {
//        photoService.deleteById(id);
//    }
//
//    @PutMapping("/{id}")
//    public Photo update(@PathVariable Integer id, @Valid @RequestBody Photo photo) {
//        photo.setId(id);
//        return photoService.update(id, photo);
//    }
}
