package com.oss.lecture6.web.controllers;

import com.oss.lecture6.web.dto.Client;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
public class ClientController {

    @GetMapping("/client/{id}")
    public String getClientById(Model model, @PathVariable  Long id) {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        var client = restTemplate.getForObject(
                "http://localhost:8080/api/client/"+id, Client.class);
        model.addAttribute("client", client);
        return "/clientTemplates/getClientTemplate";
    }
    @GetMapping("/client")
    public String getAllClients(Model model) {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        var clients = restTemplate.getForObject(
                "http://localhost:8080/api/client", Client[].class);
        model.addAttribute("clients", clients);
        return "/clientTemplates/getAllClientsTemplate";
    }
}
