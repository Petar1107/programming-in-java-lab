package com.oss.lecture6.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class ErrorController {
    @GetMapping("/customError")
    public String customError(Model model, @ModelAttribute("errorMessage") Object errorMessage) {
        model.addAttribute("msg", errorMessage);
        return "errorTemplates/customErrorTemplate";
    }
}
