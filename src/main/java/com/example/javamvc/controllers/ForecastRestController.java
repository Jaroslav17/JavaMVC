

// priima plikus do nedirba su html, dirba su duomenemys, norime kad saugotu
package com.example.javamvc.controllers;

import com.example.javamvc.models.ForecastModel;
import org.springframework.web.bind.annotation.*;


@RestController
public class ForecastRestController {
    @PostMapping(value = "/api/forecast", consumes = "application/json")
    public void index(@RequestBody ForecastModel model) {

    }
}
