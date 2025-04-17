package lk.ijse.gdse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ViewPatientHistoryDto {
    private String programId;
    private String programName;
    private String sessionId;
    private Date date;
    private Time time;
    private String status;
}
