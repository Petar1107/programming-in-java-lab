package com.oss.lecture5.controllers;

import com.oss.lecture5.dto.SmartDevicePayload;
import com.oss.lecture5.exceptions.CanNotCreateObjectException;
import com.oss.lecture5.exceptions.NotFoundException;
import com.oss.lecture5.models.History;
import com.oss.lecture5.models.SmartDevice;
import com.oss.lecture5.services.SmartDeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/api/smartDevice")
public class SmartDeviceController {
    private final SmartDeviceService smartDeviceService;

    public SmartDeviceController(SmartDeviceService smartDeviceService) {
        this.smartDeviceService = smartDeviceService;
    }

    @PostMapping
    public ResponseEntity<SmartDevice> createSmartDevice(@RequestBody SmartDevicePayload smartDevicePayload) throws CanNotCreateObjectException {
        var smartDevice = smartDeviceService.createSmartDevice(smartDevicePayload);
        if (smartDevice != null) {
            return new ResponseEntity<>(smartDevice, HttpStatus.OK);
        } else {
            throw new CanNotCreateObjectException("Can not create smart device");
        }
    }

    @GetMapping
    public List<SmartDevice> getAllSmartDevices() throws NotFoundException{
        return smartDeviceService.getAllSmartDevices();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getSmartDeviceById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(smartDeviceService.getSmartDeviceById(id));
    }

    @GetMapping("/{id}/history")
    public ResponseEntity<List<History>> getMeasurementBySmartDeviceId(@PathVariable Long id) throws NotFoundException{
        var smartDevice = smartDeviceService.getSmartDeviceById(id);
        if (smartDevice == null) {
            throw new NotFoundException("device with id: " + id + " does not exist so you can not see measurements for that device");
        } else {
            return new ResponseEntity<>(smartDevice.getHistory(), HttpStatus.OK);
        }
    }

    @GetMapping("/{id}/history/sum")
    public HashMap<String, Integer> findByYearForSmartDevice (@PathVariable Long id, @RequestParam Integer yearMeasured) throws NotFoundException{
        return smartDeviceService.findByYearForSmartDevice(id,yearMeasured);
    }

    @GetMapping("/{id}/history/byMonthAndYear")
    public List<History> findByMonthAndYearForSmartDevice (@PathVariable Long id, @RequestParam Integer yearMeasured, Integer monthMeasured) throws NotFoundException{
        return smartDeviceService.findByMonthAndYearForSmartDevice(id,yearMeasured, monthMeasured);
    }

    @GetMapping("/{id}/history/sumByMonth")
    public HashMap<Integer, Integer> sumOfMeasurementsByMonthInTheYear(@PathVariable Long id, @RequestParam Integer yearMeasured) throws NotFoundException{
        return  smartDeviceService.sumOfMeasurementsByMonthInTheYearForSmartDevice(id, yearMeasured);
    }

    @GetMapping("/{id}/history/sumByMonthString")
    public HashMap<String, Integer> sumOfMeasurementsByMonthString(@PathVariable Long id, @RequestParam Integer yearMeasured) throws NotFoundException{
        return smartDeviceService.sumOfMeasurementsByMonthStringForSmartDevice(id, yearMeasured);
    }

    @PostMapping("/{id}/history")
    public History CreateMeasurementForSmartDevice(@PathVariable Long id, @RequestBody  SmartDevicePayload smartDevicePayload) throws NotFoundException, CanNotCreateObjectException {
        return smartDeviceService.CreateMeasurementForSmartDevice(id,smartDevicePayload);
    }
}
