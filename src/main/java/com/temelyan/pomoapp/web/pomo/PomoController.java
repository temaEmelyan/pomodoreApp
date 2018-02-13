package com.temelyan.pomoapp.web.pomo;

import com.temelyan.pomoapp.to.PomoTo;
import com.temelyan.pomoapp.util.DateTimeUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PomoController extends AbstractPomoController {
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
