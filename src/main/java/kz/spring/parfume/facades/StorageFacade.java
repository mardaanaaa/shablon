package kz.spring.parfume.facades;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class StorageFacade {

    @Autowired
    @Qualifier("resourceLoaderBean")
    private ResourceLoader resourceLoader;

    public String saveFile(MultipartFile file, String fileStoragePath) throws IOException {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        Resource staticResource = resourceLoader.getResource("classpath:static" + fileStoragePath);
        String filePath = staticResource.getFile().getAbsolutePath() + File.separator + fileName;
        File destFile = new File(filePath);
        file.transferTo(destFile);
        return fileStoragePath + fileName;
    }

    public void deleteFile(String filePath) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:static" + filePath);
        Files.deleteIfExists(Paths.get(resource.getFile().getAbsolutePath()));
    }

    public boolean exists(String filePath) {
        Resource resource = resourceLoader.getResource("classpath:static" + filePath);
        return resource.exists();
    }

    public File getFile(String filePath) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:static" + filePath);
        return resource.getFile();
    }

    public void setFile(MultipartFile file, String fileStoragePath, String fileName) throws IOException {
        Resource staticResource = resourceLoader.getResource("classpath:static" + fileStoragePath);
        String filePath = staticResource.getFile().getAbsolutePath() + File.separator + fileName;
        File destFile = new File(filePath);
        file.transferTo(destFile);
    }

    @Configuration
    public static class StorageConfig {
        @Bean(name = "resourceLoaderBean")
        public ResourceLoader resourceLoader() {
            return new DefaultResourceLoader();
        }
    }
}
