package com.multiplayer.RPS.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {


    @GetMapping({"/"})
    public String getPage() {
        return "index";
    }

}
