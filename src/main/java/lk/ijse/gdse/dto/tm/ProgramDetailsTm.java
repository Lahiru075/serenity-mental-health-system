package lk.ijse.gdse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProgramDetailsTm {
    private String patient;
    private String therapyProgram;
    private String therapyProgramName;
    private double fee;
    private Date registerDate;
}
