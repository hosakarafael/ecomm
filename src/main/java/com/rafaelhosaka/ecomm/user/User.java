package com.rafaelhosaka.ecomm.user;

import lombok.Builder;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@MappedSuperclass
public abstract class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String firstName;

    private String lastName;

    private String address;

    private String phoneNumber;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateBirth;

    private String password;

    @Column(nullable = false, unique = true, length = 25)
    private String email;

    public User() {
    }

    public User(String firstName, String lastName, String address, String phoneNumber, LocalDate dateBirth, String email , String password) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.dateBirth = dateBirth;
        this.email = email;
        this.password = password;
    }
}
