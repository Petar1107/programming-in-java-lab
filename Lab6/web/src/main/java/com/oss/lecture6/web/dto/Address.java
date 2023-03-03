package com.oss.lecture6.web.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
@Data
public class Address {

    private Long id;

    private String city;

    private String street;

    private String houseNumber;

    private Long zipCode;

    private String apartment;

    private SmartDevice smartDevice;

}
