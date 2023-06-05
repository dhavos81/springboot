package com.testing.course.springboottesting.mainApp.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimplePropsController {

    @Value("${custom.hiddenName}")
    String hiddenName;

    @GetMapping("/customProp")
    public String getCustomProp(){
        return hiddenName;
    }
}
