package com.oss.vjezba4.services;

import com.oss.vjezba4.dto.SmartDevicePayload;
import com.oss.vjezba4.models.History;
import com.oss.vjezba4.repositories.HistoryRepository;
import com.oss.vjezba4.utils.Randomizer;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HistoryService {
    private final HistoryRepository historyRepository;

    private final SmartDeviceService smartDeviceService;

    public HistoryService(HistoryRepository historyRepository, SmartDeviceService smartDeviceService) {
        this.historyRepository = historyRepository;
        this.smartDeviceService = smartDeviceService;
    }


    public History CreateMeasurement(SmartDevicePayload smartDevicePayload) {
        var measurement = new History();
        measurement.setDateMeasured(new Date());
        measurement.setMeasuredValue(Randomizer.getRandomNumber());
        var smartDevice = smartDeviceService.getSmartDeviceById(smartDevicePayload.getId());
        if (smartDevice == null) return null;
        smartDevice.getHistory().add(measurement);
        measurement.setSmartDevice(smartDevice.getTitle());
        return historyRepository.save(measurement);
    }

    public List<History> getAllMeasurements() {
        return historyRepository.findAll();
    }

    public History getMeasurementById(Long id) {
        return historyRepository.findById(id).orElse(null);
    }
}
