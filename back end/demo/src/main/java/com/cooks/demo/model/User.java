package com.cooks.demo.model;

import com.cooks.demo.util.enums.UserType;
import lombok.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "user")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends Auditable<String> {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    public int id;
    private String name;

    @NonNull
    @Column(unique = true)
    @Email(message = "This is not a valid Email address!")
    private String email;

    @NonNull
    private String gender;

    @NonNull
    @Column(name = "phone_no",unique = true)
//    @Pattern(regexp = "(^$|[0-9]{10})")
    private Long phoneNumber;

    @NonNull
    private String password;

    @NonNull
    @Column(name = "user_type")
    private String userType;
}
