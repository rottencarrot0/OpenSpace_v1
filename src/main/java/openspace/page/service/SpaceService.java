package openspace.page.service;

import lombok.extern.slf4j.Slf4j;
import openspace.page.domain.Space;
import openspace.page.domain.SpaceImage;
import openspace.page.dto.space.SpaceRegister;
import openspace.page.mapper.SpaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class SpaceService {

    @Autowired
    private SpaceMapper spaceMapper;

    @Autowired
    FileUploadService fileUploadService;

    @Transactional
    public void createSpace(SpaceRegister data, List<MultipartFile> images, Long userId) throws IOException {

        Space space = new Space();
        space.setHostId(userId);
        space.setTitle(data.getTitle());
        space.setDescription(data.getDescription());
        space.setAddress(data.getAddress());
        space.setPricePerHour(data.getPricePerHour());
        space.setCapacity(data.getCapacity());

        spaceMapper.insertSpace(space);

        // 파일의 기본 정보들만 저장한다.
        List<String> urls = fileUploadService.fileUpload(images);
        for (int i = 0; i< urls.size(); i++) {
            SpaceImage img = new SpaceImage();
            img.setSpaceId(space.getId());
            img.setImageUrl(urls.get(i));
            img.setIsMain(i == 0 ? 1 : 0); // 첫 번째 이미지 main 으로 저장

        }
    }
}
