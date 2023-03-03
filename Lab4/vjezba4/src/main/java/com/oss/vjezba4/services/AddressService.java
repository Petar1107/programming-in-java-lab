package com.oss.vjezba4.services;

import com.oss.vjezba4.dto.AddressPayload;
import com.oss.vjezba4.dto.ClientPayload;
import com.oss.vjezba4.models.Address;
import com.oss.vjezba4.repositories.AddressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressService {
    private final AddressRepository addressRepository;

    private final SmartDeviceService smartDeviceService;


    public AddressService(AddressRepository addressRepository, SmartDeviceService smartDeviceService) {
        this.addressRepository = addressRepository;
        this.smartDeviceService = smartDeviceService;
    }

    public Address createAddress(AddressPayload addressPayload) {
        var address = new Address();
        address.setCity(addressPayload.getCity());
        address.setStreet(addressPayload.getStreet());
        address.setHouseNumber(addressPayload.getHouseNumber());
        address.setZipCode(addressPayload.getZipCode());
        address.setApartment(addressPayload.getApartment());
        var smartDevice = smartDeviceService.createSmartDeviceForAddress(addressPayload);
        if (smartDevice == null){
            return null;
        }
        address.setSmartDevice(smartDevice);
        return addressRepository.save(address);
    }

    public Address createAddressForClient(ClientPayload payload) {
        var address = new Address();
        address.setCity(payload.getCity());
        address.setStreet(payload.getStreet());
        address.setHouseNumber(payload.getHouseNumber());
        address.setZipCode(payload.getZipCode());
        address.setApartment(payload.getApartment());
        try {
            return addressRepository.save(address);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Address updateAddress(Address address, AddressPayload addressPayload){
        address.setCity(addressPayload.getCity());
        address.setStreet(addressPayload.getStreet());
        address.setHouseNumber(addressPayload.getHouseNumber());
        address.setZipCode(addressPayload.getZipCode());
        address.setApartment(addressPayload.getApartment());
        address.setSmartDevice(smartDeviceService.createSmartDeviceForAddress(addressPayload));
        return addressRepository.save(address);
    }
    public List<Address> getAllAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddressById(Long id) {
        return addressRepository.findById(id).orElse(null);
    }
}

