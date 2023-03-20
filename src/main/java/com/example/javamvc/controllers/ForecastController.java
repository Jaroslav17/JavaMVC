package com.example.javamvc.controllers;

import com.example.javamvc.models.ForecastModel;
import com.example.javamvc.models.IndexModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.xml.crypto.Data;
import java.util.ArrayList;

@Controller
public class ForecastController {

    @GetMapping("/")
    public ModelAndView index() {//controler
        var modelAndView = new ModelAndView("index");//view
        var indexModel = new IndexModel();

        var cities = new ArrayList<String>();
        cities.add("Vilnius");
        cities.add("Kaunas");
        indexModel.cities = cities;


        var forecasts = new ArrayList<ForecastModel>();
        forecasts.add(new ForecastModel("2023-03-20", 1));
        forecasts.add(new ForecastModel("2023-03-21", 2));
        forecasts.add(new ForecastModel("2023-03-22", 3));
        indexModel.forecasts = forecasts;


        modelAndView.addObject("IndexModel", indexModel);//Model
        return modelAndView;
    }
}


