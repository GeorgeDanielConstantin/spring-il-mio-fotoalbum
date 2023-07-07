package org.lessons.springilmiofotoalbum.service;

import org.lessons.springilmiofotoalbum.model.Photo;
import org.lessons.springilmiofotoalbum.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class PhotoService {

    @Autowired
    PhotoRepository photoRepository;

    public Page<Photo> getAll(Optional<String> keywordOpt, Pageable pageable) {
        if (keywordOpt.isEmpty()) {
            return photoRepository.findAll(pageable);
        } else {
            return photoRepository.findByTitleContainingIgnoreCase(keywordOpt.get(), pageable);
        }
    }

    public Photo getbyId(Integer id) {
        Optional<Photo> photoOpt = photoRepository.findById(id);
        if (photoOpt.isPresent()) {
            return photoOpt.get();
        } else {
            throw new RuntimeException();
        }
    }

    public Photo create(Photo photo) {
        Photo photoToPersist = new Photo();
        photoToPersist.setTitle(photo.getTitle());
        photoToPersist.setDescription(photo.getDescription());
        photoToPersist.setUrl(photo.getUrl());
        photoToPersist.setVisibility(photo.isVisibility());
        photoToPersist.setCategories(photo.getCategories());

        return photoRepository.save(photoToPersist);
    }

    public void deleteById(Integer id) {
        if (photoRepository.existsById(id)) {
            photoRepository.deleteById(id);
        } else {
            throw new RuntimeException();
        }
    }

    public Photo update(Integer id, Photo updatedPhoto) {
        Optional<Photo> photoOpt = photoRepository.findById(id);
        if (photoOpt.isPresent()) {
            Photo photo = photoOpt.get();
            photo.setTitle(updatedPhoto.getTitle());
            photo.setDescription(updatedPhoto.getDescription());
            photo.setUrl(updatedPhoto.getUrl());
            photo.setVisibility(updatedPhoto.isVisibility());
            photo.setCategories(updatedPhoto.getCategories());
            return photoRepository.save(photo);
        } else {
            throw new RuntimeException();
        }
    }
}
