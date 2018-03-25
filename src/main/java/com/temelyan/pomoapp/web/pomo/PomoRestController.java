package com.temelyan.pomoapp.web.pomo;

import com.temelyan.pomoapp.to.ProjectTo;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ajax/pomo/")
public class PomoRestController extends AbstractPomoController {
    @PostMapping(path = "add")
    public void addPomo(@RequestParam("length") int length,
                        @RequestParam("projectId") int projectId,
                        @RequestParam("clientTimeZone") int clientTimeZone) {
        super.add(length, projectId, clientTimeZone);
    }

    @GetMapping(path = "get", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProjectTo> getPomosInDateRange(@RequestParam("from") String from, @RequestParam("to") String to) {
        return super.getInDateRange(from, to);
    }
}