package com.temelyan.pomoapp.to;

import com.temelyan.pomoapp.util.DateTimeUtil;

public class PomoTo extends BaseTo {

    private Integer duration;

    private String finishDate;

    private String finishTime;

    private ProjectTo projectTo;

    public PomoTo(Integer id, Integer duration, String finishDate, String finishTime, ProjectTo projectTo) {
        super(id);
        this.duration = duration;
        this.finishDate = finishDate;
        this.finishTime = finishTime;
        this.projectTo = projectTo;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
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

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }
}
