package com.oss.lecture5.services;

import com.oss.lecture5.dto.AddressPayload;
import com.oss.lecture5.dto.ClientPayload;
import com.oss.lecture5.dto.SmartDevicePayload;
import com.oss.lecture5.exceptions.CanNotCreateObjectException;
import com.oss.lecture5.exceptions.NotFoundException;
import com.oss.lecture5.models.History;
import com.oss.lecture5.models.SmartDevice;
import com.oss.lecture5.repositories.HistoryRepository;
import com.oss.lecture5.repositories.SmartDeviceRepository;
import com.oss.lecture5.utils.Randomizer;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
public class SmartDeviceService {
    private final SmartDeviceRepository smartDeviceRepository;

    private final HistoryRepository historyRepository;

    public SmartDeviceService(SmartDeviceRepository smartDeviceRepository, HistoryRepository historyRepository) {
        this.smartDeviceRepository = smartDeviceRepository;
        this.historyRepository = historyRepository;
    }

    public SmartDevice createSmartDevice(SmartDevicePayload smartDevicePayload) {
        var smartDevice = new SmartDevice();
        smartDevice.setTitle(smartDevicePayload.getTitle());
        try{
            return smartDeviceRepository.save(smartDevice);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public SmartDevice createSmartDeviceForClient(ClientPayload smartDevicePayload) {
        var smartDevice = new SmartDevice();
        smartDevice.setTitle(smartDevicePayload.getStreet() + "_" + smartDevicePayload.getHouseNumber() + "_" + smartDevicePayload.getApartment() + "_" + smartDevicePayload.getCity() + "_" + smartDevicePayload.getZipCode());
        try{
            return smartDeviceRepository.save(smartDevice);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public SmartDevice createSmartDeviceForAddress(AddressPayload smartDevicePayload) {
        var smartDevice = new SmartDevice();
        smartDevice.setTitle(smartDevicePayload.getStreet() + "_" + smartDevicePayload.getHouseNumber() + "_" + smartDevicePayload.getApartment() + "_" + smartDevicePayload.getCity() + "_" + smartDevicePayload.getZipCode());
        try{
            return smartDeviceRepository.save(smartDevice);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public List<SmartDevice> getAllSmartDevices() throws NotFoundException{
        var smartDevices = smartDeviceRepository.findAll();
        if(smartDevices.size() != 0){
            return smartDevices;
        }
        else{
            throw new NotFoundException("Smart devices does not exist");
        }
    }

    public SmartDevice getSmartDeviceById(Long id) throws NotFoundException{
        var smartDevice = smartDeviceRepository.findById(id).orElse(null);
        if (smartDevice != null)
            return smartDevice;
        else {
            throw new NotFoundException("Smart device with id: " + id + " does not exist");
        }
    }

    public HashMap<String, Integer> findByYearForSmartDevice (Long id, Integer yearMeasured) throws NotFoundException{
        var smartDevice = smartDeviceRepository.findById(id).orElse(null);
        if (smartDevice == null){
            throw new NotFoundException("Smart device with id: " + id + " does not exist.");
        }
        var measurements = historyRepository.findByYearMeasuredAndSmartDeviceId(yearMeasured,smartDevice.getId());
        if (measurements.size() == 0) {
            throw new NotFoundException("Measurements does not exist for year: " + yearMeasured + ".");
        }
        var totalConsumption = measurements.stream().mapToInt(History::getMeasuredValue).sum();
        var map = new HashMap<String, Integer>();
        map.put("year", yearMeasured);
        map.put("total", totalConsumption);
        return map;
    }

    public List<History> findByMonthAndYearForSmartDevice (Long smartDeviceId, Integer yearMeasured, Integer monthMeasured) throws NotFoundException{
        var smartDevice = smartDeviceRepository.findById(smartDeviceId).orElse(null);
        if (smartDevice == null){
            throw new NotFoundException("Smart device with id: " + smartDeviceId + " does not exist.");
        }
        else{
            var measurements = historyRepository.findByYearMeasuredAndMonthMeasuredAndSmartDeviceId(yearMeasured, monthMeasured, smartDeviceId);
            if(measurements.size() == 0){
                throw new NotFoundException("Measurements does not exist for date: " + monthMeasured + "/" + yearMeasured);
            }
            else{
                return measurements;
            }
        }
    }

    public HashMap<Integer, Integer> sumOfMeasurementsByMonthInTheYearForSmartDevice(Long smartDeviceId, Integer yearMeasured) throws NotFoundException {
        var smartDevice = smartDeviceRepository.findById(smartDeviceId).orElse(null);
        if (smartDevice == null){
            throw new NotFoundException("Smart device with id: " + smartDeviceId + " does not exist.");
        }
        var map = new HashMap<Integer, Integer>();
        var measurements = historyRepository.findByYearMeasuredAndSmartDeviceId(yearMeasured, smartDeviceId);
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

    public HashMap<String, Integer> sumOfMeasurementsByMonthStringForSmartDevice(Long smartDeviceId, Integer yearMeasured) throws NotFoundException {
        var smartDevice = smartDeviceRepository.findById(smartDeviceId).orElse(null);
        if (smartDevice == null){
            throw new NotFoundException("Smart device with id: " + smartDeviceId + " does not exist.");
        }
        var map = new HashMap<String, Integer>();
        var measurements = historyRepository.findByYearMeasuredAndSmartDeviceId(yearMeasured, smartDeviceId);
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

    public History CreateMeasurementForSmartDevice(Long id, SmartDevicePayload smartDevicePayload) throws NotFoundException, CanNotCreateObjectException {
        var measurement = new History();
        measurement.setDateMeasured(new Date());
        var currentDate = LocalDate.now();
        measurement.setYearMeasured(currentDate.getYear());
        measurement.setMonthMeasured(currentDate.getMonth().getValue());
        measurement.setMonthMeasuredString(currentDate.getMonth());
        measurement.setMeasuredValue(Randomizer.getRandomNumber());
        var smartDevice = getSmartDeviceById(id);
        if (smartDevice == null) {
            throw new NotFoundException("Smart device with id: " + id + " does not exist.");
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

}
