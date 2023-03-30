package com.example.javamvc.controllers;

import com.example.javamvc.models.ForecastModel;
import com.example.javamvc.models.IndexModel;
import com.example.javamvc.models.Place;
import com.example.javamvc.models.Root;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

@Controller
public class ForecastController {

    @GetMapping("/")
    public ModelAndView index(@RequestParam(required = false) String cityCode) throws IOException {//controler prieamame duomenys per view
        var modelAndView = new ModelAndView("index");
        var indexModel = new IndexModel();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String userName;// istraukiame max info i perduodai i modeli ir i xtml
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        indexModel.userName = userName;


        ArrayList<Place> cities = getCities(); //miestai
        indexModel.cities = cities;

        if (cityCode != null && !cityCode.equals("")) {
            ArrayList<ForecastModel> forecasts = getForecasts(cityCode);
            indexModel.forecasts = forecasts;
        }

        if (cityCode == "") {
            cityCode = null;
        }

        indexModel.currentCityCode = cityCode;                                 // uzsetinamae ir grazinsime atgal i view

        modelAndView.addObject("IndexModel", indexModel);

        return modelAndView;
    }

    private static ArrayList<Place> getCities() throws IOException {
        var cities = new ArrayList<Place>();

        var json = loadDataJson("https://api.meteo.lt/v1/places");

        ObjectMapper om = new ObjectMapper();
        Place[] places = om.readValue(json, Place[].class);

        for (var place : places) {
            var p = new Place();
            p.code = place.code;
            p.name = place.name;
            cities.add(place);
        }

        return cities;

    }


    private static ArrayList<ForecastModel> getForecasts(String cityCode) throws IOException {
        var forecasts = new ArrayList<ForecastModel>();

        var json = loadDataJson("https://api.meteo.lt/v1/places/" + cityCode + "/forecasts/long-term");
        Root obj = createObj(json);


        // ! gaunama ir sumapiname
        for (var stamp : obj.forecastTimestamps) {
            var forecast = new ForecastModel(cityCode,stamp.forecastTimeUtc, stamp.airTemperature);
            forecasts.add(forecast);
        }

        return forecasts;
    }

    private static Root createObj(String json) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        Root obj = om.readValue(json, Root.class);
        return obj;
    }

    //reikia sumapinti is meteo kur daug info suformatuoti teksta pagal musu reikalavimus, pakeisti i viev model
    private static String loadDataJson(String apiUrl) throws IOException {
        URL url = new URL(apiUrl);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        String text = "";
        Scanner scanner = new Scanner(url.openStream());
        while (scanner.hasNext()) {
            text += scanner.nextLine();
        }
        scanner.close();
        return text;
    }
}



