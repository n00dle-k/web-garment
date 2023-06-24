package com.garderob.web_garderob.controllers;

import com.garderob.web_garderob.models.Garment;
import com.garderob.web_garderob.models.Tag;
import com.garderob.web_garderob.services.AccountService;
import com.garderob.web_garderob.services.GarmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class GarmentController extends ControllerBase {

    private final GarmentService garmentService;

    @Autowired
    public GarmentController(
            AccountService accountService,
            GarmentService garmentService) {
        super(accountService);
        this.garmentService = garmentService;
    }

    @GetMapping("/garments")
    public String getGarmentsPage(
            Authentication authentication,
            Model model) {
        final var account = getAccount(authentication);
        model.addAttribute("account", account);
        model.addAttribute("garments", account.getGarments());
        return "/garments";
    }


    @GetMapping("/garments/add")
    public String getAddGarmentPage(
            Authentication authentication,
            Model model
    ) {
        final var account = getAccount(authentication);
        model.addAttribute("account", account);
        model.addAttribute("garment", new Garment());
        model.addAttribute("selectedTags", new ArrayList<Long>());
        return "/garments/add";
    }

    @PostMapping("/garments/add")
    public String addNewGarment(
            Authentication authentication,
            @ModelAttribute("garment") Garment newGarment,
            @RequestParam("file") MultipartFile image
    ) throws IOException {
        final var account = getAccount(authentication);
        garmentService.saveNewGarment(newGarment, image, account);
        return "redirect:/garments";
    }

    @GetMapping("/garments/delete/{id}")
    public String deleteTag(
            @PathVariable("id") long id
    ) {
        garmentService.deleteById(id);
        return "redirect:/garments";
    }

    @GetMapping("/garments/edit/{id}")
    public String getEditGarmentPage(
            Authentication authentication,
            Model model,
            @PathVariable("id") long id
    ) {
        final var account = getAccount(authentication);
        model.addAttribute("account", account);
        model.addAttribute("garment", garmentService.findById(id).orElseThrow());
        return "/garments/edit";
    }

    @PostMapping("/garments/edit/{id}")
    public String updateGarment(
            Authentication authentication,
            @ModelAttribute("garment") Garment newGarment,
            @RequestParam("file") MultipartFile image
    ) throws IOException {
        final var account = getAccount(authentication);
        garmentService.updateGarment(newGarment, image, account);
        return "redirect:/garments";
    }


}
