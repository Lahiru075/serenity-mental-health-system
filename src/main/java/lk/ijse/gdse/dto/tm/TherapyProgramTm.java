package lk.ijse.gdse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TherapyProgramTm {
    private String id;
    private String name;
    private String duration;
    private String description;
    private double fee;
}
