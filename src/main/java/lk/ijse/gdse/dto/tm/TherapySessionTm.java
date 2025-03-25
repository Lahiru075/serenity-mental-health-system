package lk.ijse.gdse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TherapySessionTm {
    private String id;
    private Time time;
    private Date date;
    private String status;
    private String therapyProgram;
    private String therapists;
    private String patient;
}
