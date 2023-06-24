package com.garderob.web_garderob.controllers;

import com.garderob.web_garderob.models.Account;
import com.garderob.web_garderob.models.Tag;
import com.garderob.web_garderob.services.AccountService;
import com.garderob.web_garderob.services.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TagsController extends ControllerBase {

    private final TagService tagsService;

    @Autowired
    protected TagsController(AccountService accountService, TagService tagsService) {
        super(accountService);
        this.tagsService = tagsService;
    }

    @GetMapping("/tags")
    public String getTagsPage(Authentication authentication, Model model) {
        final var account = getAccount(authentication);
        model.addAttribute("account", account);
        model.addAttribute("tags", account.getTags());
        return "/tags";
    }

    @GetMapping("/tags/add")
    public String getAddTagPage(Authentication authentication, Model model) {
        final var account = getAccount(authentication);
        model.addAttribute("account", account);
        model.addAttribute("tag", new Tag());
        return "/tags/add";
    }

    @PostMapping("/tags/add")
    public String addNewTag(
            Authentication authentication,
            @ModelAttribute("tag") Tag newTag) {
        final var account = getAccount(authentication);
        newTag.setAccount(account);
        tagsService.saveNewTag(newTag);
        return "redirect:/tags";
    }

    @GetMapping("/tags/edit/{id}")
    public String getEditTagPage(
            Authentication authentication,
            Model model,
            @PathVariable("id") long id
    ) {
        final var account = getAccount(authentication);
        model.addAttribute("account", account);
        model.addAttribute("tag", tagsService.findById(id).orElseThrow());
        return "/tags/edit";
    }

    @PostMapping("tags/edit/{id}")
    public String editTag(
            Authentication authentication,
            Model model,
            @PathVariable("id") long id,
            @ModelAttribute("tag") Tag updatedTagState
    ) {
        tagsService.updateTagName(id, updatedTagState.getName());
        return "redirect:/tags";
    }

    @GetMapping("/tags/delete/{id}")
    public String deleteTag(
            @PathVariable("id") long id
    ) {
        tagsService.deleteById(id);
        return "redirect:/tags";
    }
}
