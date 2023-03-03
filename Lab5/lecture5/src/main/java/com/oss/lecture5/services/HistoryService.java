package com.oss.lecture5.services;

import com.oss.lecture5.dto.HistoryPayload;
import com.oss.lecture5.dto.SmartDevicePayload;
import com.oss.lecture5.exceptions.CanNotCreateObjectException;
import com.oss.lecture5.exceptions.NotFoundException;
import com.oss.lecture5.models.History;
import com.oss.lecture5.repositories.HistoryRepository;
import com.oss.lecture5.utils.Randomizer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class HistoryService {
    private final HistoryRepository historyRepository;

    private final SmartDeviceService smartDeviceService;


    public HistoryService(HistoryRepository historyRepository, SmartDeviceService smartDeviceService) {
        this.historyRepository = historyRepository;
        this.smartDeviceService = smartDeviceService;
    }

    public History CreateMeasurement(SmartDevicePayload smartDevicePayload) throws NotFoundException, CanNotCreateObjectException {
        var measurement = new History();
        measurement.setDateMeasured(new Date());
        var currentDate = LocalDate.now();
        measurement.setYearMeasured(currentDate.getYear());
        measurement.setMonthMeasured(currentDate.getMonth().getValue());
        measurement.setMonthMeasuredString(currentDate.getMonth());
        measurement.setMeasuredValue(Randomizer.getRandomNumber());
        var smartDevice = smartDeviceService.getSmartDeviceById(smartDevicePayload.getId());
        if (smartDevice == null) {
            return null;
        }
        smartDevice.getHistory().add(measurement);
        measurement.setSmartDeviceId(smartDevice.getId());
        measurement.setSmartDevice(smartDevice.getTitle());
        try {
            return historyRepository.save(measurement);

        }
        catch (Exception e){
            throw new CanNotCreateObjectException("You cannot create more measurements for device with id: " + smartDevice.getId() + " this month");
        }
    }

    public History updateMeasurement(HistoryPayload historyPayload, Long id) throws NotFoundException, CanNotCreateObjectException {
        var measurement = historyRepository.findById(id).orElse(null);
        if (measurement != null){
            try {
                measurement.setMeasuredValue(historyPayload.getMeasuredValue());
                return historyRepository.save(measurement);

            }
            catch (Exception e){
                throw new CanNotCreateObjectException("You cannot update measurement");
            }
        }
        else{
            throw new NotFoundException("Measurement with id: " + id + " does not exist");
        }
    }

    public List<History> getAllMeasurements() throws NotFoundException{
        var measurements = historyRepository.findAll();
        if(measurements.size() != 0){
            return measurements;
        }
        else{
            throw new NotFoundException("Measurements does not exist");
        }
    }

    public History getMeasurementById(Long id) throws NotFoundException{
        var measurement = historyRepository.findById(id).orElse(null);
        if (measurement != null)
            return measurement;
        else {
            throw new NotFoundException("Measurement with id: " + id + " does not exist");
        }
    }

    public String deleteMeasurementById(Long id) throws NotFoundException, CanNotCreateObjectException {
        var measurement = historyRepository.findById(id).orElse(null);
        if (measurement != null){
            try {
                historyRepository.delete(measurement);
            }
            catch (Exception e){
                throw new CanNotCreateObjectException("You cannot delete measurement");
            }
        }
        else{
            throw new NotFoundException("Measurement with id: " + id + " does not exist");
        }
        return "Measurement with id: " + id + "  successfully deleted";
    }

    public HashMap<String, Integer> findByYear (Integer yearMeasured) throws NotFoundException{
        var measurements =  historyRepository.findByYearMeasured(yearMeasured);
        if(measurements.size() == 0){
            throw new NotFoundException("Measurements does not exist for year: " + yearMeasured + ".");
        }
        var totalConsumption = measurements.stream().mapToInt(measurement -> measurement.getMeasuredValue()).sum();
        var map = new HashMap<String, Integer>();
        map.put("year", yearMeasured);
        map.put("total", totalConsumption);
        return map;
    }

    public List<History> findByMonthAndYear (Integer yearMeasured, Integer monthMeasured) throws NotFoundException{
        var measurements = historyRepository.findByYearMeasuredAndMonthMeasured(yearMeasured, monthMeasured);
        if(measurements.size() == 0){
            throw new NotFoundException("Measurements does not exist for date: " + monthMeasured + "/" + yearMeasured);
        }
        else{
            return measurements;
        }
    }

    public HashMap<Integer, Integer> sumOfMeasurementsByMonthInTheYear(Integer yearMeasured) throws NotFoundException {
        var map = new HashMap<Integer, Integer>();
        var measurements = historyRepository.findByYearMeasured(yearMeasured);
        if(measurements.size() == 0){
            throw new NotFoundException("Measurements does not exist for year: " + yearMeasured + ".");
        }
        else{
            var months = measurements.stream().map(History::getMonthMeasured).toList();
            for(var month : months){
                var totalConsumptionPerMonth = measurements.stream().filter(measurement -> measurement.getMonthMeasured() == month).mapToInt(measurement -> measurement.getMeasuredValue()).sum();
                map.put(month,totalConsumptionPerMonth);
            }
            return map;
        }
    }

    public HashMap<String, Integer> sumOfMeasurementsByMonthString(Integer yearMeasured) throws NotFoundException {
        var map = new HashMap<String, Integer>();
        var measurements = historyRepository.findByYearMeasured(yearMeasured);
        var months = measurements.stream().map(History::getMonthMeasuredString).toList();
        map.put("year", yearMeasured);
        if(measurements.size() == 0){
            throw new NotFoundException("Measurements does not exist for year: " + yearMeasured + ".");
        }
        else{
            for(var month : months){
                var totalConsumptionPerMonth = measurements.stream().filter(measurement -> measurement.getMonthMeasuredString() == month).mapToInt(measurement -> measurement.getMeasuredValue()).sum();
                map.put(month.toString().toLowerCase(),totalConsumptionPerMonth);
            }
            return map;
        }
    }
}
