package com.temelyan.pomoapp.to;

import com.temelyan.pomoapp.model.Pomo;
import com.temelyan.pomoapp.util.DateTimeUtil;

public class PomoTo {

    private Integer duration;

    private String finish;

    public PomoTo(Integer duration, String finish) {
        this.duration = duration;
        this.finish = finish;
    }

    public static PomoTo fromPomo(Pomo pomo) {
        return new PomoTo(
                pomo.getDuration(),
                pomo.getFinish().toString().replace("T", " ")
        );
    }

    public String getDurationFormattedString() {
        return DateTimeUtil.toHhMmSs(duration);
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getFinish() {
        return finish;
    }

    public void setFinish(String finish) {
        this.finish = finish;
    }
}
