package com.temelyan.pomoapp.to;

import java.util.ArrayList;
import java.util.List;

public class ProjectTo extends BaseTo {

    private String name;

    private List<PomoTo> pomoTos;

    public ProjectTo(Integer id, String name, List<PomoTo> pomoTos) {
        super(id);
        this.name = name;
        this.pomoTos = pomoTos;
    }

    public ProjectTo(Integer id, String name) {
        this(id, name, new ArrayList<>());
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
