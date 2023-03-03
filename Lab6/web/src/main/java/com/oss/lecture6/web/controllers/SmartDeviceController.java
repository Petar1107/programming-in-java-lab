package com.oss.lecture6.web.controllers;


import com.oss.lecture6.web.dto.History;
import com.oss.lecture6.web.dto.SmartDevice;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class SmartDeviceController {

    @GetMapping("/smartDevice/{id}")
    public String getSmartDeviceById(Model model, @PathVariable  Long id) {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        var smartDevice = restTemplate.getForObject(
                "http://localhost:8080/api/smartDevice/"+id, SmartDevice.class);
        var size = smartDevice.getHistory().size();
        model.addAttribute("smartDevice", smartDevice);
        model.addAttribute("history", smartDevice.getHistory());
        model.addAttribute("size", size);
        return "/smartDeviceTemplates/getSmartDeviceTemplate";
    }
    @GetMapping("/smartDevice")
    public String getAllSmartDevices(Model model) {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        var smartDevices = restTemplate.getForObject(
                "http://localhost:8080/api/smartDevice", SmartDevice[].class);
        List<History> measurements = new ArrayList<>();
        for (var smartDevice : smartDevices){
            for(var measurement : smartDevice.getHistory()){
                measurements.add(measurement);
            }
        }
        var size = measurements.size();
        model.addAttribute("measurements", measurements);
        model.addAttribute("smartDevices", smartDevices);
        model.addAttribute("size", size);
        return "/smartDeviceTemplates/getAllDevicesTemplate";
    }

    @GetMapping("smartDevice/{id}/addMeasurement")
    public String addNewMeasurement(Model model, @PathVariable Long id) {
        var currentDate = LocalDate.now();
        var measurement = new History();
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        var smartDevice = restTemplate.getForObject(
                "http://localhost:8080/api/smartDevice/"+id, SmartDevice.class);
        measurement.setSmartDeviceId(smartDevice.getId());
        model.addAttribute("measurement", measurement);
        model.addAttribute("smartDevice", smartDevice);
        model.addAttribute("currentTime", currentDate);
        var measurements = restTemplate.getForObject(
                "http://localhost:8080/api/smartDevice/history", History[].class);
        var lastId = Arrays.stream(measurements).count()+1;
        model.addAttribute("lastId", lastId);
        return "/smartDeviceTemplates/addMeasurementForSmartDevice";
    }


    @PostMapping("/saveDeviceMeasurement")
    public RedirectView saveMeasurement(@ModelAttribute("measurement") History measurement, RedirectAttributes attributes) {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<History> request = new HttpEntity<>(measurement);
        try {
            History history = restTemplate.postForObject("http://localhost:8080/api/smartDevice/" + measurement.getSmartDeviceId() + "/history", request, History.class);
            return new RedirectView("/smartDevice");
        }
        catch (Exception e) {
            attributes.addAttribute("errorMessage", e.getMessage());
            return new RedirectView("/customError");
        }
    }
}