package hu.pte.frontendkeretrendszerekbackend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
public class SaveMultipartFileToFolder {

    public void saveMultipartFileToFolder(MultipartFile multipartFile) throws IOException {
        File file = new File("/Users/balintszekely/Downloads/studev-main/frontend-keretrendszerek-backend/src/temp/temp.txt");
        InputStream inputStream = multipartFile.getInputStream();
        byte[] buffer = new byte[1024];
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();
        }
        inputStream.close();
    }
}
