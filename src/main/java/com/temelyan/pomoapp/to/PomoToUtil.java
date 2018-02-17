package com.temelyan.pomoapp.to;

import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.util.DateTimeUtil;

public class PomoToUtil {
    public static PomoTo fromEntity(Pomo pomo, ProjectTo projectTo) {
        return new PomoTo(pomo.getId(),
                pomo.getDuration(),
                DateTimeUtil.toString(pomo.getFinish()),
                projectTo
        );
    }
}
