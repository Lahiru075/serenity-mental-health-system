package lk.ijse.gdse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class PaymentDto {
    private String id;
    private double amount;
    private String status;
    private Date date;
    private String patientId;
    private String therapySessionId;
    private String therapyProgramId;
}
