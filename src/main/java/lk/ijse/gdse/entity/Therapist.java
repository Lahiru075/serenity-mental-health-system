package lk.ijse.gdse.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "therapist")
public class Therapist implements SuperEntity {
    @Id
    @Column(name = "therapist_id")
    private String id;
    private String name;
    private String email;
    private String contact;

    @ManyToMany
    @JoinTable(
            name = "therapist_therapy_program",
            joinColumns = @JoinColumn(name = "therapist_id"),
            inverseJoinColumns = @JoinColumn(name = "program_id")
    )
    private List<TherapyProgram> therapyPrograms;

    @OneToMany(mappedBy = "therapists", fetch = FetchType.LAZY)
    private List<TherapySession> therapySessions;
}
