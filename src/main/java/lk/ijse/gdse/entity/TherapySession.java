package lk.ijse.gdse.entity;

import jakarta.persistence.*;
import lombok.*;


import java.sql.Time;
import java.sql.Date;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "therapy_session")
public class TherapySession implements SuperEntity{
    @Id
    @Column(name = "therapy_session_id")
    private String id;
    private Time time;
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

    @OneToMany(mappedBy = "therapySession" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Payment> payments;

}
