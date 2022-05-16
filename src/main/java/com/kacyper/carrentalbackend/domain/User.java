package com.kacyper.carrentalbackend.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "USERS")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true)
    private Long id;

    @NotNull
    @Column(name = "FIRST_NAME")
    private String firstName;

    @NotNull
    @Column(name = "LAST_NAME")
    private String lastName;

    @NotNull
    @Column(name = "EMAIL_ADDRESS", unique = true)
    private String email;

    @NotNull
    @Column(name = "PASSWORD")
    private String password;

    @NotNull
    @Column(name = "PHONE_NUMBER", unique = true)
    private int phoneNumber;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @NotNull
    @Column(name = "ACCOUNT_CREATION_DATE")
    private LocalDate accountCreationDate;

    @OneToMany(targetEntity = Rental.class,
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH, CascadeType.REMOVE},
            fetch = FetchType.EAGER,
            mappedBy = "user")
    private List<Rental> rentalList = new ArrayList<>();

    public User(Long id, String firstName, String lastName, String email, String password, int phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}