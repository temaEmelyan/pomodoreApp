package com.temelyan.pomoapp.web.pomo;

import com.temelyan.pomoapp.model.Pomo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@SuppressWarnings("SameReturnValue")
@Controller
public class PomoController extends AbstractPomoController {
    @GetMapping("/ajax/add/{length}")
    public void addPomo(@PathVariable("length") int length) {
        super.add(length);
    }

    @GetMapping("/log")
    public String openLog(Model model) {
        List<Pomo> all = super.getAll();
        model.addAttribute("pomos", all);
        model.addAttribute("sumDuration", all.stream().mapToInt(Pomo::getDuration).sum());
        return "pomos";
    }
}
