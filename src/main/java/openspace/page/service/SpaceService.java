package openspace.page.service;

import lombok.extern.slf4j.Slf4j;
import openspace.page.domain.Space;
import openspace.page.domain.SpaceImage;
import openspace.page.dto.space.SpaceList;
import openspace.page.dto.space.SpaceRegister;
import openspace.page.mapper.SpaceImageMapper;
import openspace.page.mapper.SpaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Slf4j
@Service
public class SpaceService {

    @Autowired
    private SpaceMapper spaceMapper;

    @Autowired
    private SpaceImageMapper spaceImageMapper;

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
            spaceImageMapper.insertSpaceImage(img);
        }
    }

    /**
     *
     * @param keyword : 검색 내용
     * @param page : 현재 페이지
     * @param pageSize : 한 페이지에 보여줄 카드의 갯수
     * @return
     */
    public Map<String, Object> showSpaceList(String keyword, int page, int pageSize) {
        page = Math.max(page, 1);
        int offset = (page - 1) * pageSize;
        List<Space> spaces = spaceMapper.findSpaceListByKeyword(keyword, offset, pageSize);
        int totalCount = spaceMapper.countSpaceListByKeyword(keyword);
        int totalPage = Math.max(1, (int)Math.ceil((double)totalCount / pageSize));

        List<SpaceList> result = new ArrayList<>();
        for (Space space : spaces) {
            SpaceList spaceList = toSpaceList(space);
            result.add(spaceList);
        }

        // 화면으로 직접 나갈 내용
        Map<String, Object> map = new HashMap<>();
        map.put("totalCount", totalCount);
        map.put("currentPage", page);
        map.put("spaces", result);
        map.put("keyword", keyword);
        map.put("totalPage", totalPage);
        return map;
    }

    // 공간의 내용을 리스트에서 보여줄 내용만 뽑아온다.
    public SpaceList toSpaceList(Space space) {
        SpaceList spaceList = new SpaceList();
        spaceList.setId(space.getId());
        spaceList.setTitle(space.getTitle());
        spaceList.setAddress(space.getAddress());
        spaceList.setPricePerHour(space.getPricePerHour());
        spaceList.setCapacity(space.getCapacity());
        List<SpaceImage> images = space.getImages();
        if(images != null && !images.isEmpty()) {
            Optional<SpaceImage> mainImg = images.stream()
                    .filter(img -> img.getIsMain() == 1)
                    .findFirst();

            spaceList.setMainImageUrl(mainImg.map(SpaceImage::getImageUrl).orElse(null));
        }
        return spaceList;
    }
}
