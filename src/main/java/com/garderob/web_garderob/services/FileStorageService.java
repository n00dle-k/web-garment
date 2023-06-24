package com.garderob.web_garderob.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileStorageService {
    String uploadFile(MultipartFile avatar) throws IOException;
}
