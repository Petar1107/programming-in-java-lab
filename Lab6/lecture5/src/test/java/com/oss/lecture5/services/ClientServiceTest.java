package com.oss.lecture5.services;

import com.oss.lecture5.dto.ClientPayload;
import com.oss.lecture5.exceptions.CanNotCreateObjectException;
import com.oss.lecture5.exceptions.NotFoundException;
import com.oss.lecture5.models.Address;
import com.oss.lecture5.models.Client;
import com.oss.lecture5.models.SmartDevice;
import com.oss.lecture5.repositories.ClientRepository;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
            1L,
            "Petar",
            "Vidovic",
            "Kastela",
            "Put Jurja",
            "11",
            21210L,
            "2"
    );

    private Address mockedAddress = new Address();
    @BeforeEach
    public void init(){
        mockedClient.setId(1L);
        var address = new Address();
        address.setId(1L);
        mockedClient.setAddress(address);
    }

    @Test
    public void createClient_success() {
        Mockito.when(clientRepository.save(any(Client.class))).thenReturn(mockedClient);
        Mockito.when(addressService.createAddressForClient(any(ClientPayload.class))).thenReturn(new Address());
        Mockito.when(smartDeviceService.createSmartDeviceForClient(any(ClientPayload.class))).thenReturn(new SmartDevice());

        final var client = clientService.createClient(mockedClientPayload);
        assertEquals(client, mockedClient);
    }
    @Test
    public void createClient_failed() {
        Mockito.when(clientRepository.save(any(Client.class))).thenReturn(null);
        Mockito.when(addressService.createAddressForClient(any(ClientPayload.class))).thenReturn(new Address());
        Mockito.when(smartDeviceService.createSmartDeviceForClient(any(ClientPayload.class))).thenReturn(new SmartDevice());

        final var client = clientService.createClient(mockedClientPayload);
        assertNull(client);
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

    @Test
    public void getClientById_success() throws NotFoundException {
        Mockito.when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedClient));
        var client = clientService.getClientById(1L);
        assertEquals(mockedClient, client);
    }

    @Test
    public void getClientById_NotFound() throws NotFoundException {
        Mockito.when(clientRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> clientService.getClientById(1L)
        );
        assertTrue(thrown.getMessage().contentEquals("Client with id: " + 1 + " does not exist"));
    }

    @Test
    public void deleteClientById_notFound(){
        Mockito.when(clientRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> clientService.deleteClientById(1L)
        );
        assertTrue(thrown.getMessage().contentEquals("Client with id: " + 1 + " does not exist"));
    }

    @Test
    public void deleteClientById_canNotDelete() throws NotFoundException, CanNotCreateObjectException{
        Mockito.when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedClient));
        Mockito.doThrow(new HibernateException("test")).when(clientRepository).delete(any(Client.class));
        CanNotCreateObjectException thrown = assertThrows(
                CanNotCreateObjectException.class,
                () -> clientService.deleteClientById(1L)
        );
        assertTrue(thrown.getMessage().contentEquals("You cannot delete client"));
    }

    @Test
    public void deleteClientById_success() throws NotFoundException, CanNotCreateObjectException{
        Mockito.when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedClient));
        var str = clientService.deleteClientById(mockedClient.getId());
        assertEquals("Client with id: " + mockedClient.getId() + "  successfully deleted", str);
    }

    @Test
    public void updateClient_notFound () throws NotFoundException, CanNotCreateObjectException {
        Mockito.when(clientRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> clientService.updateClient(mockedClientPayload, 1L)
        );
        assertTrue(thrown.getMessage().contentEquals("Client with id: " + 1 + " does not exist"));
    }

    @Test
    public void updateClient_canNotUpdate () throws NotFoundException, CanNotCreateObjectException {
        Mockito.when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedClient));
        Mockito.doThrow(new HibernateException("test")).when(clientRepository).save(any(Client.class));
        CanNotCreateObjectException thrown = assertThrows(
                CanNotCreateObjectException.class,
                () -> clientService.updateClient(mockedClientPayload, 1L)
        );
        assertTrue(thrown.getMessage().contentEquals("You cannot update client"));
    }
    @Test
    public void updateClient_canNotUpdateAddress () throws NotFoundException, CanNotCreateObjectException {
        Mockito.when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedClient));
        Mockito.when(addressService.updateAddressForClient(any(ClientPayload.class), any(Long.class))).thenReturn(null);
        final var client = clientService.updateClient(mockedClientPayload, 1L);
        assertNull(client);
    }

    @Test
    public void updateClient_success () throws NotFoundException, CanNotCreateObjectException {
        Mockito.when(clientRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedClient));
        Mockito.when(addressService.updateAddressForClient(any(ClientPayload.class), any(Long.class))).thenReturn(mockedAddress);
        Mockito.when(clientRepository.save(any(Client.class))).thenReturn(mockedClient);
        var client = clientService.updateClient(mockedClientPayload, mockedClient.getId());
        assertEquals(mockedClient, client);
    }


}
