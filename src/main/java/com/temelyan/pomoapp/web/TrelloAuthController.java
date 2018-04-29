package com.temelyan.pomoapp.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class TrelloAuthController {

    public TrelloAuthController() {

        System.out.println("123");
    }

    @PostMapping("/getToken")
    public void getToken() {
        System.out.println();
    }
}
