package com.oss.vjezba4.services;

import com.oss.vjezba4.dto.ClientPayload;
import com.oss.vjezba4.models.Address;
import com.oss.vjezba4.models.Client;
import com.oss.vjezba4.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private final ClientRepository clientRepository;
    private final AddressService addressService;

    private final SmartDeviceService smartDeviceService;

    public ClientService(ClientRepository clientRepository, AddressService addressService, SmartDeviceService smartDeviceService) {
        this.clientRepository = clientRepository;
        this.addressService = addressService;
        this.smartDeviceService = smartDeviceService;
    }

    public Client createClient(ClientPayload payload) {
        var client = new Client();
        client.setFirstName(payload.getFirstName());
        client.setLastName(payload.getLastName());
        var address = addressService.createAddressForClient(payload);
        if (address == null){
            return null;
        }
        var smartDevice = smartDeviceService.createSmartDeviceForClient(payload);
        if (smartDevice == null){
            return null;
        }
        address.setSmartDevice(smartDevice);
        client.setAddress(address);
        address.setSmartDevice(smartDevice);
        try{
            return clientRepository.save(client);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id).orElse(null);
    }
}
