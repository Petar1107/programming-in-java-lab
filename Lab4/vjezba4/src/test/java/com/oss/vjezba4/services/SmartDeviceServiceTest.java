package com.oss.vjezba4.services;

import com.oss.vjezba4.dto.SmartDevicePayload;
import com.oss.vjezba4.models.SmartDevice;
import com.oss.vjezba4.repositories.SmartDeviceRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class SmartDeviceServiceTest {
    @InjectMocks
    SmartDeviceService smartDeviceService;

    @Mock
    SmartDeviceRepository smartDeviceRepository;

    private final SmartDevice mockedSmartDevice = new SmartDevice();

    private final SmartDevicePayload mockedSmartDevicePayload = new SmartDevicePayload(
            1L,
            "SmartDevice"
    );
    @Test
    public void createSmartDevice_success() {
        Mockito.when(smartDeviceRepository.save(any(SmartDevice.class))).thenReturn(mockedSmartDevice);

        final var smartDevice = smartDeviceService.createSmartDevice(mockedSmartDevicePayload);
        assertEquals(smartDevice, mockedSmartDevice);
    }
}
