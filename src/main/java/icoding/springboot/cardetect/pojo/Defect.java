package icoding.springboot.cardetect.pojo;/* I love coding */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Defect {
    //这个类相当于你写的ModelRes类
    private int defId;
    private int imgId;
    private int type;
    private String source;
    private LocalDateTime createTime;
    private String position;
//    private float confidence;
//    private List<Integer> position;


}
