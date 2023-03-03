package com.oss.vjezba4.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oss.vjezba4.dto.SmartDevicePayload;
import com.oss.vjezba4.models.SmartDevice;
import com.oss.vjezba4.services.SmartDeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
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

@WebMvcTest(SmartDeviceController.class)
public class SmartDeviceControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    private final SmartDevice mockedSmartDevice = new SmartDevice();

    private final List<SmartDevice> mockedSmartDeviceList = new ArrayList<>();

    private final SmartDevicePayload mockedSmartDevicePayload = new SmartDevicePayload(
            1L,
            "nazivUredaja"
    );

    @MockBean
    SmartDeviceService smartDeviceService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();


        Mockito.when(smartDeviceService.createSmartDevice(any(SmartDevicePayload.class))).thenReturn(mockedSmartDevice);
        Mockito.when(smartDeviceService.getSmartDeviceById(any(Long.class))).thenReturn(mockedSmartDevice);
        Mockito.when(smartDeviceService.getAllSmartDevices()).thenReturn(mockedSmartDeviceList);
    }

    @Test
    public void createSmartDevice_success() throws Exception {
        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/smartDevice")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(mockedSmartDevicePayload))
                )
                .andReturn();

        assertEquals(HttpStatus.OK.value(), res.getResponse().getStatus());
    }

    @Test
    public void createSmartDevice_failed() throws Exception {
        Mockito.when(smartDeviceService.getSmartDeviceById(any(Long.class))).thenReturn(null);

        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/smartDevice")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("")
                )
                .andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), res.getResponse().getStatus());
    }

    @Test
    public void getSmartDeviceById_success() throws Exception {
        Mockito.when(smartDeviceService.getSmartDeviceById(any(Long.class))).thenReturn(mockedSmartDevice);

        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/api/smartDevice/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andReturn();

        assertEquals(HttpStatus.OK.value(), res.getResponse().getStatus());
    }

    @Test
    public void getSmartDeviceById_failed() throws Exception {
        Mockito.when(smartDeviceService.getSmartDeviceById(any(Long.class))).thenReturn(null);

        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/api/smartDevice/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andReturn();

        assertEquals(HttpStatus.NOT_FOUND.value(), res.getResponse().getStatus());
        assertTrue(res.getResponse().getContentAsString().contains("Smart device with id: 1 does not exist"));
    }

    @Test
    public void getAllSmartDevices_success() throws Exception {
        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/api/smartDevice")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andReturn();

        assertEquals(HttpStatus.OK.value(), res.getResponse().getStatus());
    }
}

