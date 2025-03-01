package icoding.springboot.cardetect.mapper;/* I love coding */

import icoding.springboot.cardetect.pojo.CountRes;
import icoding.springboot.cardetect.pojo.Defect;
import icoding.springboot.cardetect.pojo.Img;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface DefectMapper {
    @Select("select * from defect where img_id=#{id}")
    public List<Defect> getByImgId(Integer id);

    @Delete("delete from defect where img_id=#{img_id} and def_id=#{def_id}")
    public int delete(Integer img_id,Integer def_id);

    @Insert("insert into defect (img_id,type,source,create_time,position) values (#{imgId},#{type},#{source},#{createTime},#{position})")
    public int insert(Defect defect);
    @Select("SELECT * FROM defect WHERE def_id = LAST_INSERT_ID()")
    public int get_last_insert_def();
    @Select("select type,count(*) as COUNT from defect where create_time between #{begin} and #{end} group by type")
    public List<CountRes> get_count_type(LocalDateTime begin, LocalDateTime end);
}
