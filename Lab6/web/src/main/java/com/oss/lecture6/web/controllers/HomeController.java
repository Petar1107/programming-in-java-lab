package com.oss.lecture6.web.controllers;

import com.oss.lecture6.web.dto.Client;
import com.oss.lecture6.web.dto.History;
import com.oss.lecture6.web.dto.SmartDevice;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String currentDate(Model model) {
        var millis=System.currentTimeMillis();
        var date = new java.sql.Date(millis);
        model.addAttribute("currentTime", date);
        return "/homeTemplates/dateTemplate";
    }

    @GetMapping("/info")
    public String getAllInfo(Model model) {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        var clients
                = restTemplate.getForObject(
                "http://localhost:8080/api/client", Client[].class);
        model.addAttribute("clients", clients);
        List<SmartDevice> smartDevices = new ArrayList<>();
        List<History> measurements = new ArrayList<>();
        for (var client : clients){
                smartDevices.add(client.getAddress().getSmartDevice());
        }
        for (var smartDevice : smartDevices){
            for(var measurement : smartDevice.getHistory()){
                measurements.add(measurement);
            }
        }
        var size = measurements.size();
        model.addAttribute("size", size);
        model.addAttribute("smartDevices", smartDevices);
        model.addAttribute("measurements", measurements);
        return "/homeTemplates/getAllInfo";
    }
}

