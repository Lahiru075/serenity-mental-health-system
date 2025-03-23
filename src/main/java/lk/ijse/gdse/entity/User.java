package lk.ijse.gdse.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "user")
public class User implements SuperEntity{
    @Id
    @Column(name = "user_id")
    private String id;
    private String username;
    private String password;
    private String role;
    private String email;
}
