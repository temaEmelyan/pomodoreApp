package com.temelyan.pomoapp.model

import com.temelyan.pomoapp.HasId
import javax.persistence.*

@MappedSuperclass
@Access(AccessType.FIELD)
abstract class AbstractEntity : HasId {

    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = START_SEQ)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    @Access(value = AccessType.PROPERTY)
    private var id: Int? = null

    protected constructor()

    internal constructor(id: Int?) {
        this.id = id
    }

    override fun getId(): Int? {
        return this.id
    }

    final override fun setId(id: Int) {
        this.id = id
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is AbstractEntity) return false
        val that = other as AbstractEntity?
        return id == that!!.id
    }

    override fun hashCode(): Int {
        return getId() ?: 0
    }

    override fun toString(): String {
        return String.format("%s id = %s", javaClass.simpleName, getId())
    }

    companion object {
        private const val START_SEQ = 100000
    }
}
