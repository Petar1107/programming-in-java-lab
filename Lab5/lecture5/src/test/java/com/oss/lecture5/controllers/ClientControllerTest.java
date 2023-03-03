package com.oss.lecture5.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oss.lecture5.dto.ClientPayload;
import com.oss.lecture5.exceptions.CanNotCreateObjectException;
import com.oss.lecture5.exceptions.NotFoundException;
import com.oss.lecture5.models.Client;
import com.oss.lecture5.services.ClientService;
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

@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    private MockMvc mockMvc;

    ObjectMapper objectMapper = new ObjectMapper();

    private final Client mockedClient = new Client();
    private final List<Client> mockedClientList = new ArrayList<>();

    private final ClientPayload mockedClientPayload = new ClientPayload(
            1L,
            "Petar",
            "Vidovic",
            "Kastela",
            "Put Jurja",
            "11",
            21210L,
            "2"
    );

    @MockBean
    ClientService clientService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) throws NotFoundException, CanNotCreateObjectException {
        this.mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();

        Mockito.when(clientService.createClient(any(ClientPayload.class))).thenReturn(mockedClient);
        Mockito.when(clientService.getClientById(any(Long.class))).thenReturn(mockedClient);
        Mockito.when(clientService.getAllClients()).thenReturn(mockedClientList);
        Mockito.when(clientService.updateClient(any(ClientPayload.class), any(Long.class))).thenReturn(mockedClient);
    }

    @Test
    public void createClient_success() throws Exception {
        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/client")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(mockedClientPayload))
                )
                .andReturn();

        assertEquals(HttpStatus.CREATED.value(), res.getResponse().getStatus());
    }

    @Test
    public void createClient_failed() throws Exception {
        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .post("/api/client")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("")
                )
                .andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), res.getResponse().getStatus());
    }
    @Test
    public void getClientById_success() throws Exception {
        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/api/client/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andReturn();

        assertEquals(HttpStatus.OK.value(), res.getResponse().getStatus());
    }


    @Test
    public void getAllClients_success() throws Exception {
        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/api/client")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andReturn();

        assertEquals(HttpStatus.OK.value(), res.getResponse().getStatus());
    }

    @Test
    public void deleteClient_success() throws Exception {
        Mockito.when(clientService.deleteClientById(any(Long.class))).thenReturn(any(String.class));
        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .delete("/api/client/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                )
                .andReturn();

        assertEquals(HttpStatus.OK.value(), res.getResponse().getStatus());
    }

    @Test
    public void updateClient_failed() throws Exception {
        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/api/client/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content("")
                )
                .andReturn();

        assertEquals(HttpStatus.BAD_REQUEST.value(), res.getResponse().getStatus());
    }

    @Test
    public void updateClient_success() throws Exception {
        MvcResult res = mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .put("/api/client/1")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(mockedClientPayload))
                )
                .andReturn();

        assertEquals(HttpStatus.OK.value(), res.getResponse().getStatus());
    }

}
