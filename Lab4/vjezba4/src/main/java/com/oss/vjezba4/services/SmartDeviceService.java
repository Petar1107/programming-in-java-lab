package com.oss.vjezba4.services;

import ch.qos.logback.core.encoder.EchoEncoder;
import com.oss.vjezba4.dto.AddressPayload;
import com.oss.vjezba4.dto.ClientPayload;
import com.oss.vjezba4.dto.SmartDevicePayload;
import com.oss.vjezba4.models.SmartDevice;
import com.oss.vjezba4.repositories.SmartDeviceRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SmartDeviceService {
    private final SmartDeviceRepository smartDeviceRepository;

    public SmartDeviceService(SmartDeviceRepository smartDeviceRepository) {
        this.smartDeviceRepository = smartDeviceRepository;
    }

    public SmartDevice createSmartDevice(SmartDevicePayload smartDevicePayload) {
        var smartDevice = new SmartDevice();
        smartDevice.setTitle(smartDevicePayload.getTitle());
        return smartDeviceRepository.save(smartDevice);
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
        return smartDeviceRepository.save(smartDevice);
    }

    public List<SmartDevice> getAllSmartDevices() {
        return smartDeviceRepository.findAll();
    }

    public SmartDevice getSmartDeviceById(Long id) {
        return smartDeviceRepository.findById(id).orElse(null);
    }
}
