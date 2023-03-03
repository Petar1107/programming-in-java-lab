package com.oss.lecture5.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SmartDevicePayload {
    private Long id;
    private String title;

    private Integer value;


}
