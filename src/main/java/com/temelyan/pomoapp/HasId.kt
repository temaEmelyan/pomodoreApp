package com.temelyan.pomoapp

import com.fasterxml.jackson.annotation.JsonIgnore

interface HasId {

    fun getId(): Int?

    fun setId(id: Int)

    @JsonIgnore
    fun isNew(): Boolean {
        return getId() == null
    }

}
