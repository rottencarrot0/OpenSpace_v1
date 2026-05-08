package openspace.page.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload.dir:uploads}")
    private String uploadDir;





    // 업로드 파일 정적 리소스 핸들러 등록 - /upload/
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String resourceLocation = "file:" + Paths.get(uploadDir).toAbsolutePath() +"/";
        // 윈도우 역슬레시 변환
        resourceLocation = resourceLocation.replace("\\", "/");

        registry.addResourceHandler("/upload/**")
                .addResourceLocations(resourceLocation);
    }
}
