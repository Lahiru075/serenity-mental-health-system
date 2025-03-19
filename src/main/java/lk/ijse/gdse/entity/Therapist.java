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
@Table(name = "therapist")
public class Therapist {
    @Id
    @Column(name = "therapist_id")
    private String id;
    private String name;
    private String email;
    private int contact;

    @ManyToMany(mappedBy = "therapists")
    private List<TherapyProgram> therapyPrograms;

    @OneToMany(mappedBy = "therapists")
    private List<TherapySession> therapySessions;
}
