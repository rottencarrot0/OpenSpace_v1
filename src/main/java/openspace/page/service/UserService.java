package openspace.page.service;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import openspace.page.domain.User;
import openspace.page.dto.user.UserLogin;
import openspace.page.dto.user.UserRegister;
import openspace.page.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void register(UserRegister register) {

        User user = new User();
        user.setPassword(register.getPassword());
        user.setName(register.getName());
        user.setEmail(register.getEmail());
        user.setRole(register.getRole());

        userMapper.insertUser(user);
    }

    public User login(UserLogin loginUser) {
        User findUser = userMapper.selectIoginUser(loginUser.getEmail(), loginUser.getPassword());

        log.info("findUser = {}", findUser);


        return findUser;
    }
}
