package com.oss.lecture5.controllers;

import com.oss.lecture5.dto.AddressPayload;
import com.oss.lecture5.dto.ClientPayload;
import com.oss.lecture5.dto.HistoryPayload;
import com.oss.lecture5.exceptions.CanNotCreateObjectException;
import com.oss.lecture5.exceptions.NotFoundException;
import com.oss.lecture5.models.Address;
import com.oss.lecture5.models.Client;
import com.oss.lecture5.services.AddressService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/address")
public class AddressController {
    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<Address> createAddress(@RequestBody AddressPayload addressPayload) throws CanNotCreateObjectException {
        var address = addressService.createAddress(addressPayload);
        if (address != null) {
            return new ResponseEntity<>(address, HttpStatus.OK);
        } else {
            throw new CanNotCreateObjectException("Can not create address");
        }
    }

    @GetMapping
    public List<Address> getAllAddresses() throws NotFoundException {
        return addressService.getAllAddresses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Address> getAddressById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddressById(@PathVariable Long id) throws NotFoundException, CanNotCreateObjectException{
        return ResponseEntity.ok(addressService.deleteAddressById(id));
    }

    @PutMapping("/{id}")
    public Address updateAddressForClient(@RequestBody ClientPayload payload, @PathVariable Long id) throws  NotFoundException, CanNotCreateObjectException {
        return addressService.updateAddressForClient(payload, id);
    }
}
