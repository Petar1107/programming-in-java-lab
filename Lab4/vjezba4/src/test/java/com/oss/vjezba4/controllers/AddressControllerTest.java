package com.oss.vjezba4.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oss.vjezba4.dto.AddressPayload;
import com.oss.vjezba4.models.Address;
import com.oss.vjezba4.repositories.AddressRepository;
import com.oss.vjezba4.services.AddressService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(AddressController.class)
public class AddressControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    private final Address mockedAddress = new Address();

    private final List<Address> mockedAddressList = new ArrayList<>();

    private final AddressPayload mockedAddressPayload = new AddressPayload(
            "Kastela",
            "Put Jurja",
            "11",
            21210L,
            "2"
    );

    @MockBean
    AddressService addressService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();


        when(addressService.createAddress(any(AddressPayload.class))).thenReturn(mockedAddress);
        when(addressService.getAddressById(any(Long.class))).thenReturn(mockedAddress);
        when(addressService.getAllAddresses()).thenReturn(mockedAddressList);
    }

    @Test
    public void createAddress_success() throws Exception {
        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/address")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(mockedAddressPayload))
                )
                .andReturn();

        assertEquals(HttpStatus.OK.value(), res.getResponse().getStatus());
    }

    @Test
    public void createAddress_failed() throws Exception {
        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/address")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("")
                )
                .andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), res.getResponse().getStatus());
    }

    @Test
    public void getAddressById_success() throws Exception {
        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/api/address/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andReturn();

        assertEquals(HttpStatus.OK.value(), res.getResponse().getStatus());
    }

    @Test
    public void getAddressById_failed() throws Exception {
        when(addressService.getAddressById(any(Long.class))).thenReturn(null);

        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/api/address/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), res.getResponse().getStatus());
        assertTrue(res.getResponse().getContentAsString().contains("Address with id: 1 does not exist"));
    }

    @Test
    public void getAllAddresses_success() throws Exception {
        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/api/address")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andReturn();

        assertEquals(HttpStatus.OK.value(), res.getResponse().getStatus());
    }
}

