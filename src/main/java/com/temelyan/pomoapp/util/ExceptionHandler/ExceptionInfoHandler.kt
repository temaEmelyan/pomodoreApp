package com.temelyan.pomoapp.util.ExceptionHandler

import com.temelyan.pomoapp.util.ValidationUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.core.Ordered
import org.springframework.core.annotation.Order
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RestControllerAdvice

import javax.servlet.http.HttpServletRequest

@RestControllerAdvice(annotations = [RestController::class])
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
class ExceptionInfoHandler {
    private val logger = LoggerFactory.getLogger(javaClass)

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun conflict(req: HttpServletRequest, e: DataIntegrityViolationException): ErrorInfo {
        val rootMsg = ValidationUtil.getRootCause(e).message
        if (rootMsg != null) {
            if (rootMsg.toLowerCase().contains("project_unique_name_user")) {
                return logAndGetErrorInfo(req, e, false, "Data Error", "project with this username already exists")
            }
        }
        return logAndGetErrorInfo(req, e, true, "Data error")
    }

    private fun logAndGetErrorInfo(req: HttpServletRequest, e: Exception, logException: Boolean, errorType: String, vararg details: String): ErrorInfo {
        val rootCause = ValidationUtil.getRootCause(e)
        if (logException) {
            logger.error(errorType + " at request " + req.requestURL, rootCause)
        } else {
            logger.warn("{} at request  {}: {}", errorType, req.requestURL, rootCause.toString())
        }
        return ErrorInfo(req.requestURL, errorType,
                "Data error",
                *if (details.size != 0) details else arrayOf(rootCause.toString()))
    }
}
