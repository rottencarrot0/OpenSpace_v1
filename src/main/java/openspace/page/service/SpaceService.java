package openspace.page.service;

import lombok.extern.slf4j.Slf4j;
import openspace.page.domain.Space;
import openspace.page.domain.SpaceImage;
import openspace.page.dto.space.SpaceDetail;
import openspace.page.dto.space.SpaceList;
import openspace.page.dto.space.SpaceRegister;
import openspace.page.exception.AuthenticationException;
import openspace.page.exception.ResourceNotFoundException;
import openspace.page.mapper.SpaceImageMapper;
import openspace.page.mapper.SpaceMapper;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
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

    @Transactional
    public void deleteSpace(Long spaceId, Long userId) {
        Space space = spaceMapper.findSpaceById(spaceId);
        if (space == null) {
            throw new ResourceNotFoundException("공간을 찾을 수 없습니다.");
        }
        if(!space.getHostId().equals(userId)) {
            throw new AuthenticationException("삭제할 수 있는 권한이 없습니다.");
        }
        // fk 설정 테이블 먼저 지우고
        spaceImageMapper.deleteBySpaceId(spaceId);
        // 메인 테이블 지운다.
        spaceMapper.deleteSpaceById(spaceId);
    }

    public List<SpaceList> getSpaceListByHostId(Long hostId) {
        List<Space> spaceListByHostId = spaceMapper.findSpaceListByHostId(hostId);
        List<SpaceList> result = new ArrayList<>();
        for (Space space : spaceListByHostId) {
            SpaceList spaceList = toSpaceList(space);
            result.add(spaceList);
        }
        return result;
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

    public SpaceDetail getSpaceDetail(Long id) {
        Space space = spaceMapper.findSpaceById(id);
        if(space == null) {
            throw new ResourceNotFoundException("공간을 찾을 수 없습니다.");
        }
        SpaceDetail spaceDetail = new SpaceDetail();
        spaceDetail.setId(space.getId());
        spaceDetail.setHostId(space.getHostId());
        spaceDetail.setTitle(space.getTitle());
        spaceDetail.setDescription(space.getDescription());
        spaceDetail.setAddress(space.getAddress());
        spaceDetail.setPricePerHour(space.getPricePerHour());
        spaceDetail.setCapacity(space.getCapacity());
        spaceDetail.setCreatedAt(space.getCreatedAt());
        spaceDetail.setImages(space.getImages() != null ? space.getImages() : new ArrayList<>());
        return spaceDetail;
    }
}
