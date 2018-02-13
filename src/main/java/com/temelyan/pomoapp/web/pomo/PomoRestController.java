package com.temelyan.pomoapp.web.pomo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SuppressWarnings("SameReturnValue")
@RestController
public class PomoRestController extends AbstractPomoController {
    @GetMapping("/ajax/add")
    public void addPomo(@RequestParam("length") int length) {
        super.add(length);
    }
}