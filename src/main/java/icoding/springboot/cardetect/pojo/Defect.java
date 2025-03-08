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

    private int defId;
    private int imgId;
    private int type;
    private String source;
    private String position;
    private LocalDateTime createTime;
//    private float confidence;
//    private List<Integer> position;


}
