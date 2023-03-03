package com.oss.vjezba4.services;

import com.oss.vjezba4.dto.AddressPayload;
import com.oss.vjezba4.dto.ClientPayload;
import com.oss.vjezba4.models.Address;
import com.oss.vjezba4.models.SmartDevice;
import com.oss.vjezba4.repositories.AddressRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    @InjectMocks
    AddressService addressService;

    @Mock
    AddressRepository addressRepository;

    @Mock
    SmartDeviceService smartDeviceService;

    private final Address mockedAddress = new Address();

    private final ClientPayload mockedClientPayload = new ClientPayload(
            "Petar",
            "Vidovic",
            "Kastela",
            "Put Jurja",
            "11",
            21210L,
            "2"
    );
    private final AddressPayload mockedAddressPayload = new AddressPayload(
            "Kastela",
            "Put Jurja",
            "11",
            21210L,
            "2"
    );

    @Test
    public void createAddress_success() {
        Mockito.when(addressRepository.save(any(Address.class))).thenReturn(mockedAddress);
        Mockito.when(smartDeviceService.createSmartDeviceForAddress(any(AddressPayload.class))).thenReturn(new SmartDevice());

        final var address = addressService.createAddress(mockedAddressPayload);
        assertEquals(address, mockedAddress);
    }

    @Test
    public void createAddress_failedOnSmartDevice() {
        Mockito.when(smartDeviceService.createSmartDeviceForAddress(any(AddressPayload.class))).thenReturn(null);
        final var address = addressService.createAddress(mockedAddressPayload);
        assertNull(address);
    }

    @Test
    public void createAddressForClient_success() {
        Mockito.when(addressRepository.save(any(Address.class))).thenReturn(mockedAddress);
        final var address = addressService.createAddressForClient(mockedClientPayload);
        assertEquals(address, mockedAddress);
    }



}
