package openspace.page.service;

import openspace.page.domain.User;
import openspace.page.dto.user.UserRegister;
import openspace.page.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
