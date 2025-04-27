package au.com.demo.transactions.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "auth_user")
public class User {

    @Id
    private Long id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;

    @JdbcTypeCode((SqlTypes.JSON))
    @Column(name = "roles", columnDefinition = "json")
    private List<String> roles;
}
