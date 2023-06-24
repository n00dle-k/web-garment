package com.garderob.web_garderob.services;

import com.garderob.web_garderob.models.Account;
import com.garderob.web_garderob.repo.AccountRepository;
import com.garderob.web_garderob.security.AccountDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {


    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileStorageService fileStorageService;

    @Autowired
    public AccountServiceImpl(
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder,
            FileStorageService fileStorageService) {
        this.accountRepository = accountRepository;
        this.passwordEncoder = passwordEncoder;
        this.fileStorageService = fileStorageService;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        final var oAccount = findByLogin(username);
        if (oAccount.isPresent()) {
            return new AccountDetails(oAccount.get());
        } else {
            throw new UsernameNotFoundException("There is no user with username:" + username);
        }
    }

    @Override
    public Optional<Account> findByLogin(String login) {
        return accountRepository.findByLogin(login);
    }

    @Override
    public void createNewUser(Account account, MultipartFile avatar) throws IOException {
        String resultFileName = fileStorageService.uploadFile(avatar);
        account.setImageFileName(resultFileName);
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
    }

    @Override
    public void validateNewAccount(Object target, Errors errors, MultipartFile avatar) {
        if (avatar == null || avatar.isEmpty() || avatar.getOriginalFilename() == null || avatar.getOriginalFilename().isEmpty()) {
            errors.rejectValue("imageFileName", "Avatar needs to be selected...");
        }
        final var account = (Account) target;
        if (findByLogin(account.getLogin()).isPresent()) {
            errors.rejectValue("userName", "Current username is already in use...");
        }
    }
}
