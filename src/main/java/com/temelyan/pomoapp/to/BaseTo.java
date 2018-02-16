package com.temelyan.pomoapp.to;


import com.temelyan.pomoapp.HasId;

abstract public class BaseTo implements HasId {
    protected Integer id;

    BaseTo() {
    }

    BaseTo(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }
}
