package lk.ijse.gdse.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientTm {
    private String id;
    private String name;
    private String email;
    private Date registerDate;
    private int contact;
    private String medical_history;
}
