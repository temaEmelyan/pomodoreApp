package com.temelyan.pomoapp.util

import com.temelyan.pomoapp.HasId

object ValidationUtil {

    fun checkNew(bean: HasId) {
        if (!bean.isNew()) {
            throw IllegalArgumentException("$bean must be new (id=null)")
        }
    }

    fun assureIdConsistent(bean: HasId, id: Int) {
        //      http://stackoverflow.com/a/32728226/548473
        if (bean.isNew()) {
            bean.setId(id)
        } else if (bean.getId() != id) {
            throw IllegalArgumentException("$bean must be with id=$id")
        }
    }

    //  http://stackoverflow.com/a/28565320/548473
    fun getRootCause(t: Throwable): Throwable {
        var result = t
        var cause: Throwable
        cause = result.cause!!
        while (result.cause != null && result !== cause) {
            cause = result.cause!!
            result = cause
        }
        return result
    }
}
