package com.temelyan.pomoapp.web.pomo;

import com.temelyan.pomoapp.model.Project;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("/ajax/pomo/")
public class PomoRestController extends AbstractPomoController {
    @PostMapping(path = "add")
    public void addPomo(@RequestParam("length") int length,
                        @RequestParam("taskId") int taskId,
                        @RequestParam("clientTimeZone") int clientTimeZone) {
        super.add(length, taskId, clientTimeZone);
    }

    @GetMapping(path = "get", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Project> getUserWithPomosInDateRange(@RequestParam("from") String from, @RequestParam("to") String to) {
        return super.getUserWithPomosInDateRange(from, to);
    }
}