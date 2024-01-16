package com.usefindar.app.entities.user;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, updatable = false, nullable = false)
    private Long Id;

    @NotBlank(message = "email should not be blank")
    @Column(unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Password should not be blank")
    private String password;

    @NotBlank(message = "Firstname should not be blank")
    private String firstName;

    @NotBlank(message = "lastName should not be blank")
    private String lastName;
}
