package lk.ijse.gdse.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "therapy_program")
public class TherapyProgram implements SuperEntity{
    @Id
    @Column(name = "therapy_program_id")
    private String id;
    private String name;
    private String duration;
    private String description;
    private double fee;

    @ManyToMany(mappedBy = "therapyPrograms" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Therapist> therapists;

    @OneToMany(mappedBy = "therapyProgram" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<TherapySession> therapySessions;

    @OneToMany(mappedBy = "therapyProgram" , cascade = CascadeType.ALL , fetch = FetchType.EAGER)
    private List<ProgramDetails> programDetails;

    @OneToMany(mappedBy = "therapyProgram" , cascade = CascadeType.ALL , fetch = FetchType.LAZY)
    private List<Payment> payments;


}
