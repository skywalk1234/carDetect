package icoding.springboot.cardetect.pojo;/* I love coding */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModelResponse {
    private String image;
    private int type;
    private String position;
}
