package icoding.springboot.cardetect.pojo;/* I love coding */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResDefect {
    //用来封装响应回去的缺陷
    private int defId;
    private int imgId;
    private int type;
    private String source;
    private List<List<Double>> position;//只在这里和defect类不同
    private LocalDateTime createTime;
}
