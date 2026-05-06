package openspace.page.mapper;

import openspace.page.domain.Space;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SpaceMapper {

    void insertSpace(Space space);
    Space findSpaceById(Long id);
    List<Space> findSpaceListByHostId(Long hostId);

}
