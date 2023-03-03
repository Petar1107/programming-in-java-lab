package com.oss.lecture6.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class SmartDevice {

    private Long id;

    private String title;

    private List<History> history;
}
