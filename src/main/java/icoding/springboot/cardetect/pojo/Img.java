package icoding.springboot.cardetect.pojo;/* I love coding */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Img {
    private int id;
    private String image;//保存图片的url
    private LocalDateTime inspect_time;
    private String uploader;
}
