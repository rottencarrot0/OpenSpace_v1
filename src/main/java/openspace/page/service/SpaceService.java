package openspace.page.service;

import lombok.extern.slf4j.Slf4j;
import openspace.page.dto.space.SpaceRegister;
import openspace.page.mapper.SpaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class SpaceService {

    @Autowired
    private SpaceMapper spaceMapper;

//    @Transactional
//    public Long createSpace(SpaceRegister register) {
//
//    }
}
