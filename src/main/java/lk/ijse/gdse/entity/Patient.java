package lk.ijse.gdse.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @Column(name = "patient_id")
    private String id;

    private String name;
    private String email;
    private Date registerDate;
    private int contact;
    private String medical_history;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<ProgramDetails> programDetails;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<TherapySession> therapySessions;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Payment> payments;
}
