package com.salin.kosign_eFootball_bot.config;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class InMemoryMultipartFile implements MultipartFile {
    private final String name;
    private final byte[] content;
    private final String originalFilename;
    private final String contentType;

    public InMemoryMultipartFile(String name, byte[] content, long size, String originalFilename) {
        this.name = name;
        this.content = content;
        this.originalFilename = originalFilename;
        this.contentType = "image/jpeg"; // or the appropriate content type
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getOriginalFilename() {
        return originalFilename;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public boolean isEmpty() {
        return content.length == 0;
    }

    @Override
    public long getSize() {
        return content.length;
    }

    @Override
    public byte[] getBytes() {
        return content;
    }

    @Override
    public InputStream getInputStream() {
        return new ByteArrayInputStream(content);
    }

    @Override
    public void transferTo(File dest) throws IOException, IllegalStateException {

    }
}
