package openspace.page.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileUploadService {

    @Value("${file.upload.dir}")
    String uploadDir;

    public List<String> fileUpload(List<MultipartFile> files) throws IOException {
        List<String> urls = new ArrayList<>();

        Path dirPath = Paths.get(uploadDir);
        Files.createDirectories(dirPath);

        for (MultipartFile file : files) {
            // 빈 파일 건너뜀
            if (file == null || file.isEmpty()) {
                continue;
            }

            String originalFilename = file.getOriginalFilename();
            String extension = "";
            if(originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String savedFileName = UUID.randomUUID().toString() + extension;
            // 새 경로를 만든다.
            Path savedPath = dirPath.resolve(savedFileName);
            // 저장한다.
            file.transferTo(savedPath);
            // 확인하기 위한 키워드
            urls.add("/upload/" + savedFileName);
        }
        return urls;
    }

}
