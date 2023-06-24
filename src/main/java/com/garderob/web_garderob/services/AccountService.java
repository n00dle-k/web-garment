package com.garderob.web_garderob.services;

import com.garderob.web_garderob.models.Account;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface AccountService extends UserDetailsService {
    Optional<Account> findByLogin(String userName);

    void createNewUser(Account account, MultipartFile avatar) throws IOException;

    void validateNewAccount(Object target, Errors errors, MultipartFile avatar);
}
