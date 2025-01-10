package com.scheduler.scheduler.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String redirectToScheduleList() {
        return "redirect:/schedule/list";
    }

}
