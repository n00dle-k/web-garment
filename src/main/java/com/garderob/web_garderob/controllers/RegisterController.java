package com.garderob.web_garderob.controllers;

import com.garderob.web_garderob.models.Account;
import com.garderob.web_garderob.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class RegisterController {
    public static final String AVATAR_REQUEST_PARAM = "avatar";
    private final AccountService accountService;

    @Autowired
    public RegisterController(
            AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("account", new Account());
        return "/register";
    }

    @PostMapping("/register")
    public String registerUser(
            @ModelAttribute("account") Account account,
            @RequestParam(AVATAR_REQUEST_PARAM) MultipartFile avatar,
            BindingResult bindingResult) throws IOException {
        accountService.validateNewAccount(account, bindingResult, avatar);
        if (bindingResult.hasErrors()) {
            return "/register";
        }
        accountService.createNewUser(account, avatar);
        return "rerdirect:/garments";
    }
}
