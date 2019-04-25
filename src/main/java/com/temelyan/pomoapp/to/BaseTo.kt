package com.temelyan.pomoapp.to


import com.temelyan.pomoapp.HasId

abstract class BaseTo : HasId {
    private var id: Int? = null

    internal constructor()

    internal constructor(id: Int?) {
        this.id = id
    }

    override fun getId(): Int? {
        return id
    }

    override fun setId(id: Int) {
        this.id = id
    }
}
