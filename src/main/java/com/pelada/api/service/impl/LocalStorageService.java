package com.pelada.api.service.impl;

import com.pelada.api.exception.BusinessException;
import com.pelada.api.service.StorageService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class LocalStorageService implements StorageService {
    private static final Path UPLOAD_DIR = Paths.get("uploads/profile");

    @Override
    public String upload(MultipartFile file) {
        try {
            Files.createDirectories(UPLOAD_DIR);
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            String fileName = UUID.randomUUID() + "." + extension;
            Path destination = UPLOAD_DIR.resolve(fileName);
            Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/profile/" + fileName;
        } catch (IOException e) {
            throw new BusinessException("Error uploading file.");
        }
    }

    @Override
    public void delete(String fileUrl) {
        if (fileUrl == null || fileUrl.isBlank()) {
            return;
        }

        try {
            String fileName = Paths.get(fileUrl).getFileName().toString();
            Files.deleteIfExists(UPLOAD_DIR.resolve(fileName));
        } catch (IOException e) {
            throw new BusinessException("Error deleting file.");
        }
    }
}
