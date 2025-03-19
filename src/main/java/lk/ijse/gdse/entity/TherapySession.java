package lk.ijse.gdse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "therapy_session")
public class TherapySession {
    @Id
    @Column(name = "therapy_session_id")
    private String id;
    private LocalTime time;
    private Date date;
    private String status;

    @ManyToOne
    @JoinColumn(name = "therapy_program_id")
    private TherapyProgram therapyProgram;

    @ManyToOne
    @JoinColumn(name = "therapist_id")
    private Therapist therapists;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @OneToMany(mappedBy = "therapySession")
    private List<Payment> payments;

}
