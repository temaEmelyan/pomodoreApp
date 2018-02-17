package com.temelyan.pomoapp.service;

import com.temelyan.pomoapp.model.Project;
import com.temelyan.pomoapp.to.ProjectTo;

import java.time.LocalDate;
import java.util.List;

public interface ProjectService {
    Project save(Project project);

    List<ProjectTo> getAll(int UserId);

    List<ProjectTo> getAllWithPomosInDateRange(LocalDate from, LocalDate to, int UserId);
}
