package com.oss.lecture5.dto;

import lombok.*;


@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoryPayload {
    private Integer measuredValue;
    private Integer yearMeasured;
    private Integer monthMeasured;
}
