package icoding.springboot.cardetect.mapper;

import icoding.springboot.cardetect.pojo.ModelRes;
import org.apache.ibatis.annotations.*;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface ModelResMapper {
    @Select("SELECT * FROM model_res WHERE defect_id = #{defectId}")
    ModelRes getModelRes(Integer defectId);
    @Insert("INSERT INTO model_res (defect_id, object_id, defect_type, coordinates, confidence, created_at, updated_at) " +
            "VALUES (#{defectId}, #{objectId}, #{defectType}, #{coordinates}, #{confidence}, #{createdAt}, #{updatedAt})")
    int addModelRes(ModelRes modelRes);
    @Delete("DELETE FROM model_res WHERE defect_id = #{defectId}")
    int deleteModelRes(Integer defectId);

}