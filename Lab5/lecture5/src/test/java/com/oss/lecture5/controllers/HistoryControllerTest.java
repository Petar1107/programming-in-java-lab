package com.oss.lecture5.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oss.lecture5.dto.SmartDevicePayload;
import com.oss.lecture5.exceptions.CanNotCreateObjectException;
import com.oss.lecture5.exceptions.NotFoundException;
import com.oss.lecture5.models.History;
import com.oss.lecture5.services.HistoryService;
import com.oss.lecture5.services.SmartDeviceService;
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
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(HistoryController.class)
public class HistoryControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    private final History mockedHistory = new History();

    private final List<History> mockedHistoryList = new ArrayList<>();

    private  SmartDevicePayload mockedSmartDevicePayload = new SmartDevicePayload(
            1L,
            "smartDevice"
    );

    @MockBean
    HistoryService historyService;

    @MockBean
    SmartDeviceService smartDeviceService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) throws NotFoundException, CanNotCreateObjectException {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();


        Mockito.when(historyService.CreateMeasurement(any(SmartDevicePayload.class))).thenReturn(mockedHistory);
        Mockito.when(historyService.getMeasurementById(any(Long.class))).thenReturn(mockedHistory);
        Mockito.when(historyService.getAllMeasurements()).thenReturn(mockedHistoryList);
    }

    @Test
    public void createMeasurement_success() throws Exception {
        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/smartDevice/history")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(mockedSmartDevicePayload))
                )
                .andReturn();

        assertEquals(HttpStatus.OK.value(), res.getResponse().getStatus());
    }

    @Test
    public void createMeasurement_failed() throws Exception {
        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/smartDevice/history")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("")
                )
                .andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), res.getResponse().getStatus());
    }


    @Test
    public void getAllMeasurements_success() throws Exception {
        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/api/smartDevice/history")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andReturn();

        assertEquals(HttpStatus.OK.value(), res.getResponse().getStatus());
    }

    @Test
    public void deleteMeasurement_success() throws Exception {
        Mockito.when(historyService.deleteMeasurementById(any(Long.class))).thenReturn(any(String.class));
        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .delete("/api/smartDevice/history/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andReturn();

        assertEquals(HttpStatus.OK.value(), res.getResponse().getStatus());
    }

    @Test
    public void updateMeasurement_failed() throws Exception {
        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/api/smartDevice/history/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("")
                )
                .andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), res.getResponse().getStatus());
    }


}

