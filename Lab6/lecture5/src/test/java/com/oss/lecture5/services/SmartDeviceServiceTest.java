package com.oss.lecture5.services;

import com.oss.lecture5.dto.SmartDevicePayload;
import com.oss.lecture5.exceptions.NotFoundException;
import com.oss.lecture5.models.History;
import com.oss.lecture5.models.SmartDevice;
import com.oss.lecture5.repositories.HistoryRepository;
import com.oss.lecture5.repositories.SmartDeviceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class SmartDeviceServiceTest {
    @InjectMocks
    SmartDeviceService smartDeviceService;

    @Mock
    SmartDeviceRepository smartDeviceRepository;

    @Mock
    HistoryRepository historyRepository;

    @Mock
    HistoryService historyService;

    private final SmartDevice mockedSmartDevice = new SmartDevice();

    private final SmartDevicePayload mockedSmartDevicePayload = new SmartDevicePayload(
            1L,
            "SmartDevice",
            1
    );

    private final List<History> mockedHistoryList = new ArrayList<>();
    @BeforeEach
    void init() {
        var h1 = new History();
        h1.setYearMeasured(2022);
        h1.setMeasuredValue(300);
        h1.setMonthMeasured(12);
        h1.setMonthMeasuredString(Month.DECEMBER);
        var h2 = new History();
        h2.setYearMeasured(2022);
        h2.setMeasuredValue(200);
        h2.setMonthMeasured(2);
        h2.setMonthMeasuredString(Month.FEBRUARY);
        var h3 = new History();
        h3.setYearMeasured(2022);
        h3.setMeasuredValue(200);
        h3.setMonthMeasured(12);
        h3.setMonthMeasuredString(Month.DECEMBER);
        mockedHistoryList.add(h1);
        mockedHistoryList.add(h2);
        mockedHistoryList.add(h3);
        mockedSmartDevice.setId(1L);
        mockedSmartDevice.setTitle("smartDevice");
        mockedSmartDevice.setHistory(mockedHistoryList);
    }

    @Test
    public void createSmartDevice_success() {
        Mockito.when(smartDeviceRepository.save(any(SmartDevice.class))).thenReturn(mockedSmartDevice);

        final var smartDevice = smartDeviceService.createSmartDevice(mockedSmartDevicePayload);
        assertEquals(smartDevice, mockedSmartDevice);
    }

    @Test
    public void findByYearForSmartDevice_success() throws NotFoundException{
        Mockito.when(smartDeviceRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedSmartDevice));
        Mockito.when(historyRepository.findByYearMeasuredAndSmartDeviceId(any(Integer.class), any(Long.class))).thenReturn(mockedHistoryList);
        var historyList = smartDeviceService.findByYearForSmartDevice(1L, 2022);
        assertEquals(historyList.get("year"), 2022);
        assertEquals(historyList.get("total"), 700);

    }
    @Test
    public void findByYearForSmartDevice_notFound() throws NotFoundException{
        Mockito.when(smartDeviceRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedSmartDevice));
        Mockito.when(historyRepository.findByYearMeasuredAndSmartDeviceId(any(Integer.class), any(Long.class))).thenReturn(new ArrayList<>());
        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> smartDeviceService.findByYearForSmartDevice(1L, 2022)
        );
        assertTrue(thrown.getMessage().contentEquals("Measurements does not exist for year: " + 2022 + "."));

    }

    @Test
    public void findByYearForSmartDevice_failedOnSmartDevice() throws NotFoundException{
        Mockito.when(smartDeviceRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> smartDeviceService.findByYearForSmartDevice(1L, 2022)
        );
        assertTrue(thrown.getMessage().contentEquals("Smart device with id: " + 1L + " does not exist."));
    }

    @Test
    public void  findByMonthAndYearForSmartDevice_success () throws NotFoundException{
        Mockito.when(smartDeviceRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedSmartDevice));
        Mockito.when(historyRepository.findByYearMeasuredAndMonthMeasuredAndSmartDeviceId(any(Integer.class), any(Integer.class), any(Long.class))).thenReturn(mockedHistoryList);
        var historyList = smartDeviceService.findByMonthAndYearForSmartDevice(1L, 2022, 12);
        assertEquals(historyList, mockedHistoryList);
    }

    @Test
    public void  findByMonthAndYearForSmartDevice_notFound () throws NotFoundException{
        Mockito.when(smartDeviceRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedSmartDevice));
        Mockito.when(historyRepository.findByYearMeasuredAndMonthMeasuredAndSmartDeviceId(any(Integer.class), any(Integer.class), any(Long.class))).thenReturn(new ArrayList<>());
        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> smartDeviceService.findByMonthAndYearForSmartDevice(1L, 2022, 12)
        );
        assertTrue(thrown.getMessage().contentEquals("Measurements does not exist for date: " + 12 + "/" + 2022));

    }

    @Test
    public void  findByMonthAndYearForSmartDevice_failedOnSmartDevice () throws NotFoundException{
        Mockito.when(smartDeviceRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> smartDeviceService.findByMonthAndYearForSmartDevice(1L, 2022, 12)
        );
        assertTrue(thrown.getMessage().contentEquals("Smart device with id: " + 1 + " does not exist."));

    }

    @Test
    public void sumOfMeasurementsByMonthInTheYearForSmartDevice_success () throws NotFoundException {
        Mockito.when(smartDeviceRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedSmartDevice));
        Mockito.when(historyRepository.findByYearMeasuredAndSmartDeviceId(any(Integer.class), any(Long.class))).thenReturn(mockedHistoryList);
        var historyList = smartDeviceService.sumOfMeasurementsByMonthInTheYearForSmartDevice(1L, 2022);
        assertEquals(historyList.get(12), 500 );
        assertEquals(historyList.get(2), 200 );
    }

    @Test
    public void sumOfMeasurementsByMonthStringForSmartDevice_success () throws NotFoundException {
        Mockito.when(smartDeviceRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedSmartDevice));
        Mockito.when(historyRepository.findByYearMeasuredAndSmartDeviceId(any(Integer.class), any(Long.class))).thenReturn(mockedHistoryList);
        var historyList = smartDeviceService.sumOfMeasurementsByMonthStringForSmartDevice(1L, 2022);
        assertEquals(historyList.get("december"), 500 );
        assertEquals(historyList.get("february"), 200 );
    }
    @Test
    public void sumOfMeasurementsByMonthInTheYearForSmartDevice_notFound () throws NotFoundException {
        Mockito.when(smartDeviceRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedSmartDevice));
        Mockito.when(historyRepository.findByYearMeasuredAndSmartDeviceId(any(Integer.class), any(Long.class))).thenReturn(new ArrayList<>());
        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> smartDeviceService.sumOfMeasurementsByMonthInTheYearForSmartDevice(1L, 2022)
        );
        assertTrue(thrown.getMessage().contentEquals("Measurements does not exist for year: " + 2022 + "."));
    }

    @Test
    public void sumOfMeasurementsByMonthInTheYearForSmartDevice_failedOnSmartDevice () throws NotFoundException {
        Mockito.when(smartDeviceRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> smartDeviceService.sumOfMeasurementsByMonthInTheYearForSmartDevice(1L, 2022)
        );
        assertTrue(thrown.getMessage().contentEquals("Smart device with id: " + 1 + " does not exist."));
    }


    @Test
    public void sumOfMeasurementsByMonthStringForSmartDevice_notFound () throws NotFoundException {
        Mockito.when(smartDeviceRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedSmartDevice));
        Mockito.when(historyRepository.findByYearMeasuredAndSmartDeviceId(any(Integer.class), any(Long.class))).thenReturn(new ArrayList<>());
        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> smartDeviceService.sumOfMeasurementsByMonthStringForSmartDevice(1L, 2022)
        );
        assertTrue(thrown.getMessage().contentEquals("Measurements does not exist for year: " + 2022 + "."));
    }

    @Test
    public void sumOfMeasurementsByMonthStringForSmartDevice_failedOnSmartDevice () throws NotFoundException {
        Mockito.when(smartDeviceRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> smartDeviceService.sumOfMeasurementsByMonthStringForSmartDevice(1L, 2022)
        );
        assertTrue(thrown.getMessage().contentEquals("Smart device with id: " + 1 + " does not exist."));
    }

}
