package com.oss.lecture5.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.Month;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"yearMeasured", "monthMeasured", "SmartDevice"})})
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "dateMeasured")
    private Date dateMeasured;

    @Column(name = "yearMeasured", nullable = false)
    private Integer yearMeasured;

    @Column(name = "monthMeasured", nullable = false)
    private Integer monthMeasured;

    @Column(name = "monthMeasuredString")
    private Month monthMeasuredString;

    @Column(name = "measuredValue", nullable = false)
    private Integer measuredValue;

    @Column(name = "smartDevice")
    private String SmartDevice;

    @Column(name = "smartDeviceId")
    private Long smartDeviceId;

}
