package lk.ijse.gdse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data

public class InvoiceDto {
    private String patientId;
    private String patientName;
    private String programName;
    private String sessionId;
    private Date sessionDate;
    private Date invoiceDate;
    private double fee;
    private String status;
}
