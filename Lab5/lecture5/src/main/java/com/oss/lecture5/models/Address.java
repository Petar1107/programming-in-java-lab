package com.oss.lecture5.models;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@Getter
@Setter
@Entity
@NoArgsConstructor
@Builder
@Data
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"city","street","houseNumber","zipCode","apartment"})})
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "houseNumber", nullable = false)
    private String houseNumber;

    @Column(name = "zipCode", nullable = false)
    private Long zipCode;

    @Column(name = "apartment", nullable = false)
    private String apartment;

    @OneToOne
    @JoinColumn(unique = true)
    private SmartDevice smartDevice;

}
