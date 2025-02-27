package icoding.springboot.cardetect.service;/* I love coding */

import icoding.springboot.cardetect.pojo.User;

public interface UserService {
    public User login(String username, String password);
    public int register(String username, String password);
    public void update_info(String new_username,String old_username,String password);
}
