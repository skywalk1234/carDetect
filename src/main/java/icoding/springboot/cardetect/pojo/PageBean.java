package icoding.springboot.cardetect.pojo;/* I love coding */

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PageBean {
    private Integer total;
    private List rows;
}
