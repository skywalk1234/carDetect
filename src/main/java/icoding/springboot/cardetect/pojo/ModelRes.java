package icoding.springboot.cardetect.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelRes {
    private Integer defectId;
    private Integer objectId;
    private String defectType;
    private List<List<Integer>> coordinates;
    private Double confidence;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
