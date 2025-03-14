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

    private int def_id;
    private int img_id;
    private int type;
    private String source;
    private String position;
    private LocalDateTime create_time;
//    private float confidence;
//    private List<Integer> position;


}
