package com.garderob.web_garderob.controllers;

import com.garderob.web_garderob.models.Account;
import com.garderob.web_garderob.services.AccountService;
import org.springframework.security.core.Authentication;

public abstract class ControllerBase {

    private final AccountService accountService;

    protected ControllerBase(AccountService accountService) {
        this.accountService = accountService;
    }

    protected AccountService getAccountService() {
        return accountService;
    }

    protected Account getAccount(Authentication authentication) {
        return getAccountService().findByLogin(authentication.getName()).orElseThrow();
    }
}
