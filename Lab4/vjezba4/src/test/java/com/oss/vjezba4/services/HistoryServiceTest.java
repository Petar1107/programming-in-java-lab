package com.oss.vjezba4.services;

import com.oss.vjezba4.dto.AddressPayload;
import com.oss.vjezba4.dto.SmartDevicePayload;
import com.oss.vjezba4.models.History;
import com.oss.vjezba4.models.SmartDevice;
import com.oss.vjezba4.repositories.HistoryRepository;
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
public class HistoryServiceTest {
    @InjectMocks
    HistoryService historyService;

    @Mock
    HistoryRepository historyRepository;

    @Mock
    SmartDeviceService smartDeviceService;

    private final History mockedHistory = new History();

    private final SmartDevicePayload mockedSmartDevicePayload = new SmartDevicePayload(
            1L,
            "smartDevice"
    );


    @Test
    public void createMeasurement_failedOnSmartDevice() {
        Mockito.when(smartDeviceService.getSmartDeviceById(any(Long.class))).thenReturn(null);

        final var address = historyService.CreateMeasurement(mockedSmartDevicePayload);
        assertNull(address);
    }

}
