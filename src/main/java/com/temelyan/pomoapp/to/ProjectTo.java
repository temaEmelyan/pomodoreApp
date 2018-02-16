package com.temelyan.pomoapp.to;

import com.temelyan.pomoapp.model.Project;

import java.util.List;
import java.util.stream.Collectors;

public class ProjectTo extends BaseTo {

    private Integer id;

    private String name;

    private List<PomoTo> pomoTos;

    public ProjectTo(int id, String name, List<PomoTo> pomoTos) {
        super(id);
        this.name = name;
        this.pomoTos = pomoTos;
    }

    public static ProjectTo fromProject(Project project) {
        return new ProjectTo(project.getId(),
                project.getName(),
                project.getPomo().stream().map(PomoTo::fromPomo).collect(Collectors.toList()));
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PomoTo> getPomoTos() {
        return pomoTos;
    }

    public void setPomoTos(List<PomoTo> pomoTos) {
        this.pomoTos = pomoTos;
    }
}
