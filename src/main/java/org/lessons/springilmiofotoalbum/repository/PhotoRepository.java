package org.lessons.springilmiofotoalbum.repository;

import org.lessons.springilmiofotoalbum.model.Photo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PhotoRepository extends JpaRepository<Photo, Integer> {

    Page<Photo> findByTitleContainingIgnoreCase(String title, Pageable pageable);

}
