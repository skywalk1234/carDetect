package icoding.springboot.cardetect.service.impl;/* I love coding */

import icoding.springboot.cardetect.mapper.UserMapper;
import icoding.springboot.cardetect.pojo.User;
import icoding.springboot.cardetect.service.UserService;
import icoding.springboot.cardetect.utils.MYSQL_;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class UserserviceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsernameAndPassword(username, password);
        return user;
    }

    @Override
    public int register(String username, String password) {
       return userMapper.regist(username, password);

    }

    @Override
    public void update_info(String new_username, String old_username, String password) {
        userMapper.update(new_username,old_username,password);
    }


}
