package com.oss.lecture6.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
public class Client {
    private Long id;

    private String firstName;

    private String lastName;

    private Address address;

}
