package com.oss.lecture5.services;

import com.oss.lecture5.dto.HistoryPayload;
import com.oss.lecture5.dto.SmartDevicePayload;
import com.oss.lecture5.exceptions.CanNotCreateObjectException;
import com.oss.lecture5.exceptions.NotFoundException;
import com.oss.lecture5.models.Client;
import com.oss.lecture5.models.History;
import com.oss.lecture5.models.SmartDevice;
import com.oss.lecture5.repositories.HistoryRepository;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class HistoryServiceTest {
    @Spy
    @InjectMocks
    HistoryService historyService;

    @Mock
    HistoryRepository historyRepository;

    @Mock
    SmartDeviceService smartDeviceService;

    private final History mockedHistory = new History();

    private final SmartDevice mockedSmartDevice = new SmartDevice();

    private final SmartDevicePayload mockedSmartDevicePayload = new SmartDevicePayload(
            1L,
            "smartDevice",
            1
    );

    private final HistoryPayload mockedHistoryPayload = new HistoryPayload();

    private final List<History> mockedHistoryList = new ArrayList<>();


    @BeforeEach
    void init() {
        var h1 = new History();
        h1.setYearMeasured(2022);
        h1.setMeasuredValue(100);
        h1.setMonthMeasured(12);
        var h2 = new History();
        h2.setYearMeasured(2022);
        h2.setMeasuredValue(200);
        h2.setMonthMeasured(2);
        var h3 = new History();
        h3.setYearMeasured(2022);
        h3.setMeasuredValue(200);
        h3.setMonthMeasured(12);
        mockedHistoryList.add(h1);
        mockedHistoryList.add(h2);
        mockedHistoryList.add(h3);
        mockedHistory.setYearMeasured(2022);
        mockedHistory.setMeasuredValue(120);
        mockedHistory.setMonthMeasured(12);
        mockedHistory.setId(1L);
        mockedHistoryPayload.setMeasuredValue(200);
        mockedHistoryPayload.setYearMeasured(2022);
        mockedHistoryPayload.setMonthMeasured(12);
        mockedSmartDevice.setId(1L);
        mockedSmartDevice.setTitle("smartDevice");
        mockedSmartDevice.setHistory(mockedHistoryList);



    }


    @Test
    public void createMeasurement_failedOnSmartDevice() throws NotFoundException, CanNotCreateObjectException {
        Mockito.when(smartDeviceService.getSmartDeviceById(any(Long.class))).thenReturn(null);

        final var measurement = historyService.CreateMeasurement(mockedSmartDevicePayload);
        assertNull(measurement);
    }

    @Test
    public void createMeasurement_success() throws NotFoundException, CanNotCreateObjectException {
        Mockito.when(smartDeviceService.getSmartDeviceById(any(Long.class))).thenReturn(mockedSmartDevice);
        Mockito.when(historyRepository.save(any(History.class))).thenReturn(mockedHistory);
        var history = historyService.CreateMeasurement(mockedSmartDevicePayload);
        assertEquals(mockedHistory, history);
    }

    @Test
    public void createMeasurement_canNotCreate() throws NotFoundException, CanNotCreateObjectException {
        Mockito.when(smartDeviceService.getSmartDeviceById(any(Long.class))).thenReturn(mockedSmartDevice);
        Mockito.when(historyService.CreateMeasurement(mockedSmartDevicePayload)).thenThrow(new CanNotCreateObjectException("You cannot create more measurements for device with id: " + mockedSmartDevice.getId() + " this month"));
        CanNotCreateObjectException thrown = assertThrows(
                CanNotCreateObjectException.class,
                () -> historyService.CreateMeasurement(mockedSmartDevicePayload)
        );
        assertTrue(thrown.getMessage().contentEquals("You cannot create more measurements for device with id: " + mockedSmartDevice.getId() + " this month"));
    }

    @Test
    public void findByYear_notFound() throws NotFoundException{
        Mockito.when(historyRepository.findByYearMeasured(any(Integer.class))).thenReturn(new ArrayList<>());
        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> historyService.findByYear(2022)
        );
        assertTrue(thrown.getMessage().contentEquals("Measurements does not exist for year: " + 2022 + "."));

    }

    @Test
    public void findByYear_success() throws NotFoundException{
        Mockito.when(historyRepository.findByYearMeasured(any(Integer.class))).thenReturn(mockedHistoryList);
        var measurements = historyService.findByYear(2022);
        assertEquals(measurements.get("year"), 2022);
        assertEquals(measurements.get("total"), 500);
    }

    @Test
    public void sumOfMeasurementsByMonthInTheYear_success() throws NotFoundException{
        Mockito.when(historyRepository.findByYearMeasured(any(Integer.class))).thenReturn(mockedHistoryList);
        var measurements = historyService.sumOfMeasurementsByMonthInTheYear(2022);
        assertEquals(measurements.get(12), 300);
        assertEquals(measurements.get(2), 200);
    }

   @Test
    public void sumOfMeasurementsByMonthInTheYear_notFound() throws NotFoundException {
        Mockito.when(historyRepository.findByYearMeasured(any(Integer.class))).thenReturn(new ArrayList<>());
        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> historyService.findByYear(2022)
        );
        assertTrue(thrown.getMessage().contentEquals("Measurements does not exist for year: " + 2022 + "."));
    }

    @Test
    public void deleteMeasurementById_NotFound() throws CanNotCreateObjectException, NotFoundException{
        Mockito.when(historyRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> historyService.deleteMeasurementById(1L)
        );
        assertTrue(thrown.getMessage().contentEquals("Measurement with id: " + 1 + " does not exist"));
    }
    @Test
    public void deleteMeasurementById_CanNotDelete() throws CanNotCreateObjectException, NotFoundException {
        Mockito.when(historyRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedHistory));
        Mockito.doThrow(new HibernateException("test")).when(historyRepository).delete(any(History.class));
        CanNotCreateObjectException thrown = assertThrows(
                CanNotCreateObjectException.class,
                () -> historyService.deleteMeasurementById(1L)
        );
        assertTrue(thrown.getMessage().contentEquals("You cannot delete measurement"));
    }

    @Test
    public void deleteMeasurementById_success() throws CanNotCreateObjectException, NotFoundException{
        Mockito.when(historyRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedHistory));
        var str = historyService.deleteMeasurementById(mockedHistory.getId());
        assertEquals("Measurement with id: " + mockedHistory.getId() + "  successfully deleted", str);
    }

    @Test
    public void updateMeasurement_notFound() throws NotFoundException, CanNotCreateObjectException {
        Mockito.when(historyRepository.findById(any(Long.class))).thenReturn(Optional.empty());
        NotFoundException thrown = assertThrows(
                NotFoundException.class,
                () -> historyService.updateMeasurement(mockedHistoryPayload, 1L)
        );
        assertTrue(thrown.getMessage().contentEquals("Measurement with id: " + 1L + " does not exist"));
    }

    @Test
    public void updateMeasurement_success() throws NotFoundException, CanNotCreateObjectException {
        Mockito.when(historyRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedHistory));
        Mockito.when(historyRepository.save(any(History.class))).thenReturn(mockedHistory);
        var history = historyService.updateMeasurement(mockedHistoryPayload, 1L);
        assertEquals(history.getMeasuredValue(), mockedHistory.getMeasuredValue());
    }

    @Test
    public void updateMeasurement_canNotUpdate() throws NotFoundException, CanNotCreateObjectException {
        Mockito.when(historyRepository.findById(any(Long.class))).thenReturn(Optional.of(mockedHistory));
        Mockito.when(historyRepository.save(any(History.class))).thenThrow(new HibernateException("test"));
        CanNotCreateObjectException thrown = assertThrows(
                CanNotCreateObjectException.class,
                () -> historyService.updateMeasurement(mockedHistoryPayload, mockedHistory.getId())
        );
        assertTrue(thrown.getMessage().contentEquals("You cannot update measurement"));
    }
}
