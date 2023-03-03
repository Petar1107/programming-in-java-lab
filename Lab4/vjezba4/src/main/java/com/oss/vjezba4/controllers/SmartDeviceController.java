package com.oss.vjezba4.controllers;

import com.oss.vjezba4.dto.SmartDevicePayload;
import com.oss.vjezba4.models.SmartDevice;
import com.oss.vjezba4.services.SmartDeviceService;
import com.oss.vjezba4.utils.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/smartDevice")
public class SmartDeviceController {
    private final SmartDeviceService smartDeviceService;

    public SmartDeviceController(SmartDeviceService smartDeviceService) {
        this.smartDeviceService = smartDeviceService;
    }

    @PostMapping
    public ResponseEntity<?> createSmartDevice(@RequestBody SmartDevicePayload smartDevicePayload) {
        var smartDevice = smartDeviceService.createSmartDevice(smartDevicePayload);
        if (smartDevice != null) {
            return new ResponseEntity<>(smartDevice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage("Can not create smart device"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<SmartDevice> getAllSmartDevices() {
        return smartDeviceService.getAllSmartDevices();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getSmartDeviceById(@PathVariable Long id) {
        var smartDevice = smartDeviceService.getSmartDeviceById(id);
        if (smartDevice != null) {
            return new ResponseEntity<>(smartDevice, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage("Smart device with id: " + id + " does not exist"), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<?> getMeasurementBySmartDeviceId(@PathVariable Long id) {
        if (smartDeviceService.getSmartDeviceById(id) == null) {
            return new ResponseEntity<>(new ResponseMessage("device with id: " + id + " does not exist so you can not see measurements for that device"), HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(smartDeviceService.getSmartDeviceById(id).getHistory(), HttpStatus.OK);
        }
    }

}
