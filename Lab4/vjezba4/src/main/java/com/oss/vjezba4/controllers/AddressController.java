package com.oss.vjezba4.controllers;

import com.oss.vjezba4.dto.AddressPayload;
import com.oss.vjezba4.models.Address;
import com.oss.vjezba4.services.AddressService;
import com.oss.vjezba4.utils.ResponseMessage;
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
    public ResponseEntity<?> createAddress(@RequestBody AddressPayload addressPayload) {
        var address = addressService.createAddress(addressPayload);
        if (address != null) {
            return new ResponseEntity<>(address, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage("Can not create address"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public List<Address> getAllAddresses() {
        return addressService.getAllAddresses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAddressById(@PathVariable Long id) {
        var address = addressService.getAddressById(id);
        if (address != null) {
            return new ResponseEntity<>(address, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(new ResponseMessage("Address with id: " + id + " does not exist"), HttpStatus.NOT_FOUND);
        }
    }
}
