package com.oss.vjezba4.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClientPayload {
    private String firstName;
    private String lastName;
    private String city;
    private String street;
    private String houseNumber;
    private Long zipCode;
    private String apartment;
}
