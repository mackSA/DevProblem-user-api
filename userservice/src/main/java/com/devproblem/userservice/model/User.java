package com.devproblem.userservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@Builder
@NoArgsConstructor @AllArgsConstructor
public class User {



    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @Column(name = "id_number", nullable = false)
    private String idNumber;

    @Column(name = "physical_address", nullable = false)
    private String address;



}
