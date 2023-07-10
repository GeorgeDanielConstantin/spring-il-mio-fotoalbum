package org.lessons.springilmiofotoalbum.controller;

import jakarta.validation.Valid;
import org.lessons.springilmiofotoalbum.messages.AlertMessage;
import org.lessons.springilmiofotoalbum.messages.AlertMessageType;
import org.lessons.springilmiofotoalbum.model.Photo;
import org.lessons.springilmiofotoalbum.repository.CategoryRepository;
import org.lessons.springilmiofotoalbum.repository.PhotoRepository;
import org.lessons.springilmiofotoalbum.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;


@Controller
@CrossOrigin
@RequestMapping("/photo")
public class PhotoController {

    @Autowired
    PhotoService photoService;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    @CrossOrigin
    public String list(
            @RequestParam(name = "keyword", required = false) String searchString,
            Model model) {
        List<Photo> photos;

        if (searchString == null || searchString.isBlank()) {
            photos = photoRepository.findAll();
        } else {
            photos = photoRepository.findByTitleContainingIgnoreCase(searchString);
        }

        model.addAttribute("photosList", photos);
        model.addAttribute("searchInput", searchString == null ? "" : searchString);
        return "/photo/list";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {

        Photo photo = getPhotoById(id);
        model.addAttribute("photo", photo);
        return "/photo/detail";

    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("photo", new Photo());
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "/photo/edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("photo") Photo formPhoto,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Model model) {
        if (bindingResult.hasErrors()) {
            return "/photo/edit";
        }
        photoRepository.save(formPhoto);
        redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessageType.SUCCESS, "Foto " + formPhoto.getTitle() + " aggiunta!"));
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "redirect:/photo";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Photo photo = getPhotoById(id);
        model.addAttribute("photo", photo);
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "photo/edit";
    }

    @PostMapping("/edit/{id}")
    public String doEdit(@PathVariable Integer id,
                         @Valid @ModelAttribute("photo") Photo formPhoto,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model) {
        Photo photo = getPhotoById(id);
        if (bindingResult.hasErrors()) {
            return "/photo/edit";
        }
        formPhoto.setId(photo.getId());
        photoRepository.save(formPhoto);
        redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessageType.SUCCESS, "Foto " + formPhoto.getTitle() + " modificata!"));
        model.addAttribute("categoryList", categoryRepository.findAll());
        return "redirect:/photo";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        Photo photo = getPhotoById(id);
        photoRepository.delete(photo);
        redirectAttributes.addFlashAttribute("message", new AlertMessage(AlertMessageType.SUCCESS, "Foto " + photo.getTitle() + " cancellata!"));
        return "redirect:/photo";
    }

    private Photo getPhotoById(Integer id) {
        Optional<Photo> result = photoRepository.findById(id);
        if (result.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Foto con id " + id + " non trovata");
        }
        return result.get();
    }
}
