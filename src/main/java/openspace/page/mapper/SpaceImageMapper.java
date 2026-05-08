package openspace.page.mapper;

import openspace.page.domain.SpaceImage;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SpaceImageMapper {


    void insertSpaceImage(SpaceImage img);
}
