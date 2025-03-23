package lk.ijse.gdse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "therapy_program")
public class TherapyProgram {
    @Id
    @Column(name = "therapy_program_id")
    private String id;
    private String name;
    private String duration;
    private String description;
    private double fee;

    @ManyToMany
    @JoinTable (
            name = "therapist_therapy_program",
            joinColumns = @JoinColumn(name = "program_id"),
            inverseJoinColumns = @JoinColumn(name = "therapist_id")
    )
    private List<Therapist> therapists;

    @OneToMany(mappedBy = "therapyProgram")
    private List<TherapySession> therapySessions;

    @OneToMany(mappedBy = "therapyProgram")
    private List<ProgramDetails> programDetails;

    @OneToMany(mappedBy = "therapyProgram")
    private List<Payment> payments;


}
