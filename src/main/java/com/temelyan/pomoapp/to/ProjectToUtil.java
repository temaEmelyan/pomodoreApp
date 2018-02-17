package com.temelyan.pomoapp.to;

import com.temelyan.pomoapp.model.Project;

public class ProjectToUtil {
    public static ProjectTo fromEntity(Project project) {
        return new ProjectTo(project.getId(), project.getName());
    }
}
