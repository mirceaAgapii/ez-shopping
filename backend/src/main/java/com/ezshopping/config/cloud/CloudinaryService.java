package com.ezshopping.config.cloud;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class CloudinaryService {
    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public Map upload(MultipartFile multipartFile)  {
        File file = toFile(multipartFile);
        Map result = null;
        try {
            result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
            file.delete();
            return result;
        }
        catch (IOException e) {
            log.info("Something went wrong ex {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public Map delete(String id)  {
        try {
            return  cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        }
        catch (IOException e) {
            log.info("Something went wrong ex {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private File toFile(MultipartFile multipartFile) {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        FileOutputStream fo = null;
        try {
            fo = new FileOutputStream(file);
            fo.write(multipartFile.getBytes());
            fo.close();
            return file;
        }
        catch (IOException e) {
            log.info("Something went wrong ex {}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
