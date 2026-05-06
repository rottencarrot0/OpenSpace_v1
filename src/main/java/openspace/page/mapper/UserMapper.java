package openspace.page.mapper;

import openspace.page.domain.User;
import openspace.page.dto.user.UserLogin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    void insertUser(User user);
    User selectUserByEmail(String email);
    User selectIoginUser(String email, String password);
}
