package com.oss.vjezba4.services;

import com.oss.vjezba4.dto.ClientPayload;
import com.oss.vjezba4.models.Address;
import com.oss.vjezba4.models.Client;
import com.oss.vjezba4.models.SmartDevice;
import com.oss.vjezba4.repositories.ClientRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {
    @InjectMocks
    ClientService clientService;
    @Mock
    ClientRepository clientRepository;

    @Mock
    AddressService addressService;

    @Mock
    SmartDeviceService smartDeviceService;

    private final Client mockedClient = new Client();

    private ClientPayload mockedClientPayload = new ClientPayload(
            "Petar",
            "Vidovic",
            "Kastela",
            "Put Jurja",
            "11",
            21210L,
            "2"
    );

    @Test
    public void createClient_success() {
        Mockito.when(clientRepository.save(any(Client.class))).thenReturn(mockedClient);
        Mockito.when(addressService.createAddressForClient(any(ClientPayload.class))).thenReturn(new Address());
        Mockito.when(smartDeviceService.createSmartDeviceForClient(any(ClientPayload.class))).thenReturn(new SmartDevice());

        final var client = clientService.createClient(mockedClientPayload);
        assertEquals(client, mockedClient);
    }

    @Test
    public void createClient_failedOnAddress() {
        Mockito.when(addressService.createAddressForClient(any(ClientPayload.class))).thenReturn(null);

        final var client = clientService.createClient(mockedClientPayload);
        assertNull(client);
    }

    @Test
    public void createClient_failedOnSmartDevice() {
        Mockito.when(addressService.createAddressForClient(any(ClientPayload.class))).thenReturn(new Address());
        Mockito.when(smartDeviceService.createSmartDeviceForClient(any(ClientPayload.class))).thenReturn(null);

        final var client = clientService.createClient(mockedClientPayload);
        assertNull(client);
    }
}
