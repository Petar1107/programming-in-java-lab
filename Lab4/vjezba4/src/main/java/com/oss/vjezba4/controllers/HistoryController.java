package com.oss.vjezba4.controllers;

import com.oss.vjezba4.dto.SmartDevicePayload;
import com.oss.vjezba4.models.History;
import com.oss.vjezba4.services.HistoryService;
import com.oss.vjezba4.services.SmartDeviceService;
import com.oss.vjezba4.utils.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/smartDevice/history")
public class HistoryController {
    private final HistoryService historyService;

    private final SmartDeviceService smartDeviceService;

    public HistoryController(HistoryService historyService, SmartDeviceService smartDeviceService) {
        this.historyService = historyService;
        this.smartDeviceService = smartDeviceService;
    }

    @PostMapping
    public ResponseEntity<?> createMeasurement(@RequestBody SmartDevicePayload payload) {
        var measurement = historyService.CreateMeasurement(payload);
        if (measurement != null) {
            return new ResponseEntity<>(measurement, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage("You cannot make a measurement because device with id: " + payload.getId() + " does not exist" ), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMeasurementById(@PathVariable(value = "id") Long id) {
        var history = historyService.getMeasurementById(id);
        if (history != null) {
            return new ResponseEntity<>(history, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage("Measurement with id: " + id + " does not exist"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public List<History> getAllMeasurements() {
        return historyService.getAllMeasurements();
    }
}
