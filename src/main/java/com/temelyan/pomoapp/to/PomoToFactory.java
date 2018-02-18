package com.temelyan.pomoapp.to;

import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.util.DateTimeUtil;

public class PomoToFactory {
    public static PomoTo fromEntity(Pomo pomo, ProjectTo projectTo) {
        return new PomoTo(pomo.getId(),
                pomo.getDuration(),
                DateTimeUtil.toString(pomo.getFinish().toLocalDate()),
                DateTimeUtil.toString(pomo.getFinish().toLocalTime()),
                projectTo
        );
    }
}