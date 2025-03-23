package lk.ijse.gdse.entity;

import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "program_details")
public class ProgramDetails implements SuperEntity {

    @EmbeddedId
    private ProgramDetailsId id;

    @ManyToOne
    @MapsId("patientId")
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne
    @MapsId("therapyProgramId")
    @JoinColumn(name = "therapy_program_id")
    private TherapyProgram therapyProgram;


    private String therapyProgramName;
}
