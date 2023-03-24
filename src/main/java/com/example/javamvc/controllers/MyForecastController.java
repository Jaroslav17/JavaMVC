package com.example.javamvc.controllers;

import com.example.javamvc.repositories.ForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;


@Controller
public class MyForecastController {

    @Autowired
    private ForecastRepository forecastRepository;

    @GetMapping("/my-forecasts")
    public ModelAndView index(@RequestParam(required = false) String cityCode) throws IOException {//controler prieamame duomenys per view
        var modelAndView = new ModelAndView("myForecasts");
        var model = forecastRepository.findAll();


        modelAndView.addObject("myForecasts", model);
        return modelAndView;
    }

}



