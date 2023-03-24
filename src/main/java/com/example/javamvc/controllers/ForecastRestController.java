

// priima plikus duomenys nedirba su html, dirba su duomenemys, norime kad saugotu
package com.example.javamvc.controllers;

import com.example.javamvc.models.Forecast;
import com.example.javamvc.models.ForecastModel;
import com.example.javamvc.repositories.ForecastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class ForecastRestController {

    @Autowired
    private ForecastRepository forecastRepository;

    @PostMapping(value = "/api/forecast", consumes = "application/json")
    public void index(@RequestBody ForecastModel model) {


        Forecast entity = new Forecast(
                model.date,
                String.valueOf(model.temperatura),
                "Vilnius",
                1);


        forecastRepository.save(entity);




    }
}
