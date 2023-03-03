package com.oss.lecture5.models;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "firstName")
    private String firstName;

    @Column(name = "lastName")
    @NotNull(message = "Firstname shouldn't be null")
    private String lastName;

    @OneToOne
    @JoinColumn(unique = true)
    private Address address;

//    @OneToOne
//    @JoinColumn(unique = true)
//    private SmartDevice smartDevice;

}
