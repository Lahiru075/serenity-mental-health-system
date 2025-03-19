package lk.ijse.gdse.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Embeddable
@Data
public class ProgramDetailsId {
    private String therapyProgramId;
    private String patientId;
}
