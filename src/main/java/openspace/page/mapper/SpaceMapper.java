package openspace.page.mapper;

import jakarta.validation.Valid;
import openspace.page.domain.Space;
import openspace.page.dto.space.SpaceRegister;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SpaceMapper {

    void insertSpace(Space space);
    Space findSpaceById(Long id);
    List<Space> findSpaceListByHostId(Long hostId);
    List<Space> findSpaceListByKeyword(@Param("keyword") String keyword,
                                       @Param("offset")int offset,
                                       @Param("limit")int pageSize);
    int countSpaceListByKeyword(String keyword);

    void deleteSpaceById(Long spaceId);

    void updateSpace(Space space);

    List<Space> findLatestSpace(int limit);
}
