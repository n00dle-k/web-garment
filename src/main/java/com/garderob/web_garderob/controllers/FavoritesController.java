package com.garderob.web_garderob.controllers;

import com.garderob.web_garderob.dto.NewFavouriteDto;
import com.garderob.web_garderob.models.FavoriteLook;
import com.garderob.web_garderob.services.AccountService;
import com.garderob.web_garderob.services.GarmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FavoritesController extends ControllerBase {

    private final GarmentService garmentsService;

    @Autowired
    public FavoritesController(AccountService accountService, GarmentService garmentService) {
        super(accountService);
        this.garmentsService = garmentService;
    }

    @GetMapping("/favorites")
    public String getFavoritesPage(Authentication authentication, Model model) {
        final var account = getAccount(authentication);
        model.addAttribute("looks", garmentsService.getFavoritesForUserId(account.getId()));
        model.addAttribute("account", account);
        return "/favorites";
    }

    @PostMapping("favorites/delete/{id}")
    public String deleteFavorite(
            Authentication authentication,
            @PathVariable("id") long id
    ){
        garmentsService.deleteFavoriteById(id);
        return "redirect:/favorites";
    }

    @PostMapping("/favorites/add")
    public String saveToFavorites(
            Authentication authentication,
            @ModelAttribute("newLook") NewFavouriteDto newLook) {
        final var account = getAccount(authentication);
        final var newFavorite = new FavoriteLook();
        newFavorite.setAccount(account);
        if (newLook.getBottomId() != 0) {
            newFavorite.setBottom(garmentsService.findById(newLook.getBottomId()).orElseThrow());
        }
        if (newLook.getShoes() != 0) {
            newFavorite.setShoes(garmentsService.findById(newLook.getShoes()).orElseThrow());
        }
        if (newLook.getDress() != 0) {
            newFavorite.setDress(garmentsService.findById(newLook.getDress()).orElseThrow());
        }
        if (newLook.getTop1layerId() != 0) {
            newFavorite.setTop1Layer(garmentsService.findById(newLook.getTop1layerId()).orElseThrow());
        }
        if (newLook.getTop2layerId() != 0) {
            newFavorite.setTop2Layer(garmentsService.findById(newLook.getTop2layerId()).orElseThrow());
        }
        garmentsService.saveAsFavorites(newFavorite);
        return "redirect:/favorites";
    }
}
