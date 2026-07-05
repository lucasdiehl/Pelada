package com.pelada.api.service;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String upload(MultipartFile file);
    void delete(String fileName);
}
