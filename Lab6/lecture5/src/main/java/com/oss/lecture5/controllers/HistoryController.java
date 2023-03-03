package com.oss.lecture5.controllers;

import com.oss.lecture5.dto.HistoryPayload;
import com.oss.lecture5.dto.SmartDevicePayload;
import com.oss.lecture5.exceptions.CanNotCreateObjectException;
import com.oss.lecture5.exceptions.NotFoundException;
import com.oss.lecture5.models.History;
import com.oss.lecture5.repositories.HistoryRepository;
import com.oss.lecture5.services.HistoryService;
import com.oss.lecture5.utils.ApplicationConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(path = "/api/smartDevice/history")
public class HistoryController {
    private final HistoryService historyService;

    private final HistoryRepository historyRepository;


    public HistoryController(HistoryService historyService, HistoryRepository historyRepository) {
        this.historyService = historyService;
        this.historyRepository = historyRepository;
    }

    @PostMapping
    public ResponseEntity<History> createMeasurement(@RequestBody SmartDevicePayload payload) throws NotFoundException, CanNotCreateObjectException {
        var measurement = historyService.CreateMeasurement(payload);
        if (measurement != null) {
            return new ResponseEntity<>(measurement, HttpStatus.OK);
        } else {
            throw new CanNotCreateObjectException("You cannot make a measurement because device with id: " + payload.getId() + " does not exist");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<History> getMeasurementById(@PathVariable(value = "id") Long id) throws NotFoundException {
        var history = historyService.getMeasurementById(id);
        if (history != null) {
            return new ResponseEntity<>(history, HttpStatus.OK);
        } else {
           throw new NotFoundException("Measurement with id: " + id + " does not exist");
        }
    }

    @GetMapping
    public List<History> getAllMeasurements(
    @RequestParam (value = "pageNumber",  defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER,  required = false) Integer pageNumber ,
    @RequestParam (value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE,  required = false) Integer pageSize,
    @RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
    @RequestParam(value = "sortDirection", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDirection) throws NotFoundException
    {
        return historyService.getAllMeasurements(pageNumber, pageSize, sortBy, sortDirection);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMeasurementById(@PathVariable Long id) throws NotFoundException, CanNotCreateObjectException{
        return ResponseEntity.ok(historyService.deleteMeasurementById(id));
    }

    @PutMapping("/{id}")
    public History updateMeasurement(@RequestBody HistoryPayload payload, @PathVariable Long id) throws  NotFoundException, CanNotCreateObjectException {
        return historyService.updateMeasurement(payload, id);
    }

    @GetMapping("/sumByYear")
    public HashMap<String, Integer> findByYear(@RequestParam Integer yearMeasured) throws NotFoundException{
         return historyService.findByYear(yearMeasured);
    }

    @GetMapping("/byMonthAndYear")
    public List<History> findByMonthAndYear (@RequestParam Integer yearMeasured, Integer monthMeasured) throws NotFoundException{
        return historyService.findByMonthAndYear(yearMeasured, monthMeasured);
    }

    @GetMapping("/sumByMonth")
    public HashMap<Integer, Integer> sumOfMeasurementsByMonthInTheYear(@RequestParam Integer yearMeasured) throws NotFoundException{
        return  historyService.sumOfMeasurementsByMonthInTheYear(yearMeasured);
    }

    @GetMapping("/sumByMonthString")
    public HashMap<String, Integer> sumOfMeasurementsByMonthString(@RequestParam Integer yearMeasured) throws NotFoundException{
        return historyService.sumOfMeasurementsByMonthString(yearMeasured);
    }
}
