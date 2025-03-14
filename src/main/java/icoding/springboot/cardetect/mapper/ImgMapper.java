package icoding.springboot.cardetect.mapper;/* I love coding */

import icoding.springboot.cardetect.pojo.Img;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Mapper
public interface ImgMapper {
    @Insert("insert into imgs values (#{id},#{image},#{inspect_time},#{uploader})")
    public void insert(Img img);
    @Select("SELECT * FROM imgs WHERE id = LAST_INSERT_ID()")
    public Img get_last_insert_img();

    public Set<Img> findAll(Integer id,Integer type, String uploader, LocalDateTime begin, LocalDateTime end);
    public int deleteById(List<Integer> ids);
    public int deleteByDefId(List<Integer> ids);
}
