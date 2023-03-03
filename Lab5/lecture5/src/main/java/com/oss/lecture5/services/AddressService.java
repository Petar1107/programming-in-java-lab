package com.oss.lecture5.services;

import com.oss.lecture5.dto.AddressPayload;
import com.oss.lecture5.dto.ClientPayload;
import com.oss.lecture5.exceptions.CanNotCreateObjectException;
import com.oss.lecture5.exceptions.NotFoundException;
import com.oss.lecture5.models.Address;
import com.oss.lecture5.repositories.AddressRepository;
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

    public Address updateAddressForClient(ClientPayload payload, Long id) throws NotFoundException, CanNotCreateObjectException {
        var address = addressRepository.findById(id).orElse(null);
        if (address != null){
            var smartDeviceId = address.getSmartDevice().getId();
            var smartDevice = smartDeviceService.getSmartDeviceById(smartDeviceId);
            address.setCity(payload.getCity());
            address.setStreet(payload.getStreet());
            address.setHouseNumber(payload.getHouseNumber());
            address.setZipCode(payload.getZipCode());
            address.setApartment(payload.getApartment());
            smartDevice.setTitle(payload.getStreet() + "_" + payload.getHouseNumber() + "_" + payload.getApartment() + "_" + payload.getCity() + "_" + payload.getZipCode());
            address.setSmartDevice(smartDevice);
            try {
                return addressRepository.save(address);

            }
            catch (Exception e){
                throw new CanNotCreateObjectException("You cannot update address");
            }
        }
        else{
            throw new NotFoundException("Address with id: " + id + " does not exist");
        }
    }

    public List<Address> getAllAddresses() throws NotFoundException{
        var addresses = addressRepository.findAll();
        if(addresses.size()!=0){
            return addresses;
        }
        else{
            throw new NotFoundException("Addresses does not exist");
        }
    }

    public Address getAddressById(Long id) throws NotFoundException{
        var address = addressRepository.findById(id).orElse(null);
        if(address != null){
            return address;
        }
        else {
            throw new NotFoundException("Client with id: " + id + " does not exist");
        }
    }

    public String deleteAddressById(Long id) throws NotFoundException, CanNotCreateObjectException{
        var address = addressRepository.findById(id).orElse(null);
        if (address != null){
            try {
                addressRepository.delete(address);
            }
            catch (Exception e){
                throw new CanNotCreateObjectException("You cannot delete address");
            }
        }
        else{
            throw new NotFoundException("Address with id: " + id + " does not exist");
        }
        return "Address with id: " + id + "  successfully deleted";
    }
}

