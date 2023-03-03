package com.oss.lecture6.web.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.time.Month;
@Setter
@Getter
@NoArgsConstructor
public class History {

    private Long id;

    private Integer yearMeasured;

    private Integer monthMeasured;

    private Integer measuredValue;

    private String SmartDevice;

    private Long smartDeviceId;

    private Integer value;

    public History(Integer measuredValue) {
        this.measuredValue = measuredValue;
    }
}
