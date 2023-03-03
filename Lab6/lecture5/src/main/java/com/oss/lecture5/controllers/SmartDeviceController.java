package com.oss.lecture5.controllers;

import com.oss.lecture5.dto.SmartDevicePayload;
import com.oss.lecture5.exceptions.CanNotCreateObjectException;
import com.oss.lecture5.exceptions.NotFoundException;
import com.oss.lecture5.models.History;
import com.oss.lecture5.models.SmartDevice;
import com.oss.lecture5.services.SmartDeviceService;
import com.oss.lecture5.utils.ApplicationConstants;
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
    public List<SmartDevice> getAllMeasurements(
            @RequestParam (value = "pageNumber",  defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER,  required = false) Integer pageNumber ,
            @RequestParam (value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE,  required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) throws NotFoundException
    {
        return smartDeviceService.getAllSmartDevices(pageNumber, pageSize, sortBy, sortDirection);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getSmartDeviceById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(smartDeviceService.getSmartDeviceById(id));
    }

    @GetMapping("/{id}/history")
    public List<History> getAllMeasurements(@PathVariable Long id,
            @RequestParam (value = "pageNumber",  defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER,  required = false) Integer pageNumber ,
            @RequestParam (value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE,  required = false) Integer pageSize,
            @RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDirection", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) throws NotFoundException
    {
        return smartDeviceService.getMeasurementsBySmartDeviceId(id,pageNumber, pageSize, sortBy, sortDirection);
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

    @DeleteMapping("/{id}/history/{measurementId}")
    public ResponseEntity<String> deleteMeasurementById(@PathVariable Long id, @PathVariable int measurementId) throws NotFoundException, CanNotCreateObjectException{
        return ResponseEntity.ok(smartDeviceService.deleteMeasurementForSmartDevice(id, measurementId));
    }
}
