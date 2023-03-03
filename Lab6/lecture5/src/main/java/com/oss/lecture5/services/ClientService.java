package com.oss.lecture5.services;

import com.oss.lecture5.dto.ClientPayload;
import com.oss.lecture5.exceptions.CanNotCreateObjectException;
import com.oss.lecture5.exceptions.NotFoundException;
import com.oss.lecture5.models.Client;
import com.oss.lecture5.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

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
        try{
            return clientRepository.save(client);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Client updateClient (ClientPayload payload, Long id) throws NotFoundException, CanNotCreateObjectException {
        var client = clientRepository.findById(id).orElse(null);
        if (client != null){
            var addressId = client.getAddress().getId();
            var address = addressService.updateAddressForClient(payload, addressId);
            client.setAddress(address);
            client.setFirstName(payload.getFirstName());
            client.setLastName(payload.getLastName());
            try {
                return clientRepository.save(client);
            }
            catch (Exception e){
                throw new CanNotCreateObjectException("You cannot update client");
            }
        }
        else{
            throw new NotFoundException("Client with id: " + id + " does not exist");
        }
    }

    public List<Client> getAllClients(Integer pageNumber, Integer pageSize, String sortBy, String sortDirection)  throws NotFoundException {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.DESC.name()) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        var clientPage = clientRepository.findAll(pageable);
        if(clientPage.toList().size() != 0){
            return clientPage.toList();
        }
        else{
            throw new NotFoundException("Client does not exist");
        }
    }

    public Client getClientById(Long id) throws NotFoundException {
        var client = clientRepository.findById(id).orElse(null);
        if (client != null)
            return client;
        else {
            throw new NotFoundException("Client with id: " + id + " does not exist");
        }
    }

    public String deleteClientById(Long id) throws NotFoundException, CanNotCreateObjectException {
        var clientForDelete = clientRepository.findById(id).orElse(null);
        if (clientForDelete != null){
            var addressForDelete = clientForDelete.getAddress().getId();
            try {
                clientRepository.delete(clientForDelete);
            }
            catch (Exception e){
                throw new CanNotCreateObjectException("You cannot delete client");
            }
            addressService.deleteAddressById(addressForDelete);
        }
        else{
            throw new NotFoundException("Client with id: " + id + " does not exist");
        }
        return "Client with id: " + id + "  successfully deleted";
    }

}
