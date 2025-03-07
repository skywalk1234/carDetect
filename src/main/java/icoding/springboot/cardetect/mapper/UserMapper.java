package icoding.springboot.cardetect.mapper;/* I love coding */

import icoding.springboot.cardetect.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserMapper {
    @Select("select * from user where username=#{username} and password=#{password}")
    public User findByUsernameAndPassword(String username, String password);

    @Select("select permission from user where username=#{username}")
    public String findPermission(String username);

    @Insert("insert into user (username, password) values (#{username},#{password})")
    public int regist(String username, String password);

    @Update("update user set username=#{new_username},password=#{password} where username=#{old_username}")
    public void update(String new_username, String old_username, String password);
}
