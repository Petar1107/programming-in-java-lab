package com.oss.lecture5.controllers;


import com.oss.lecture5.dto.ClientPayload;
import com.oss.lecture5.exceptions.CanNotCreateObjectException;
import com.oss.lecture5.exceptions.NotFoundException;
import com.oss.lecture5.models.Address;
import com.oss.lecture5.models.Client;
import com.oss.lecture5.services.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/api/client")
public class ClientController {
    private final ClientService clientService;

    ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody @Valid ClientPayload payload) throws CanNotCreateObjectException {
        var client = clientService.createClient(payload);
        if (client != null) {
            return new ResponseEntity<>(client, HttpStatus.CREATED);
        } else {
           throw new CanNotCreateObjectException("Can not create client");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) throws NotFoundException {
        return ResponseEntity.ok(clientService.getClientById(id));
    }

    @GetMapping
    public List<Client> getAllClients() throws NotFoundException {
        return clientService.getAllClients();
    }

    @DeleteMapping("/{id}")
   public ResponseEntity<String> deleteClientById(@PathVariable Long id) throws NotFoundException, CanNotCreateObjectException{
        return ResponseEntity.ok(clientService.deleteClientById(id));
   }

    @PutMapping("/{id}")
    public Client updateClient (@RequestBody ClientPayload payload, @PathVariable Long id) throws  NotFoundException, CanNotCreateObjectException {
        return clientService.updateClient(payload, id);
    }
}
