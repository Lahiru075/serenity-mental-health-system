package lk.ijse.gdse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProgramDetailsDto {
    private String patient;
    private String therapyProgram;
    private String therapyProgramName;
    private double currentPaymentStatus;
    private Date registerDate;
}
