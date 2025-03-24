package lk.ijse.gdse.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TherapistTherapyPrograms implements SuperEntity {
    String therapyProgramId;
    String therapistId;
}
