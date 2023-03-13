package com.example.javamvc.controllers;

import org.springframework.web.servlet.ModelAndView;

public class ForecastController {
    public ModelAndView index() {
        var modelAndView = new ModelAndView("index");

        return modelAndView;
    }
}
