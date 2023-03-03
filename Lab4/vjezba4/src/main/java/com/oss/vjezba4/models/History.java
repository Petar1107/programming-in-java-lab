package com.oss.vjezba4.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "dateMeasured", nullable = false)
    private Date dateMeasured;

    @Column(name = "measuredValue", nullable = false)
    private Integer measuredValue;

    @Column(name = "smartDevice")
    private String SmartDevice;
}
