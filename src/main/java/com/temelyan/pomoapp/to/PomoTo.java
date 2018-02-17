package com.temelyan.pomoapp.to;

import com.temelyan.pomoapp.util.DateTimeUtil;

public class PomoTo extends BaseTo {

    private Integer duration;

    private String finish;
    private ProjectTo projectTo;

    public PomoTo(Integer id, Integer duration, String finish, ProjectTo projectTo) {
        super(id);
        this.duration = duration;
        this.finish = finish;
        this.projectTo = projectTo;
    }

    public ProjectTo getProjectTo() {
        return projectTo;
    }

    public void setProjectTo(ProjectTo projectTo) {
        this.projectTo = projectTo;
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
