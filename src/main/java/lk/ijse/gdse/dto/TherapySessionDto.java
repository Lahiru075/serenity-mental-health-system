package lk.ijse.gdse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TherapySessionDto {
    private String id;
    private Time time;
    private Date date;
    private String status;
    private String therapyProgramId;
    private String therapistsId;
    private String patientId;
}
