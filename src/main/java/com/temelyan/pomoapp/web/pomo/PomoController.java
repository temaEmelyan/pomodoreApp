package com.temelyan.pomoapp.web.pomo;

import com.temelyan.pomoapp.to.PomoTo;
import com.temelyan.pomoapp.util.DateTimeUtil;
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
        List<PomoTo> all = super.getAll();
        model.addAttribute("pomos", all);
        model.addAttribute("sumDuration",
                DateTimeUtil.toHhMmSs(
                        all.stream()
                                .mapToInt(PomoTo::getDuration)
                                .sum()
                )
        );
        return "pomos";
    }
}
