package com.luizmatias.workout_tracker.config.exception.controller

import com.luizmatias.workout_tracker.dto.common.ErrorResponseDTO
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView
import java.util.*

@Controller
@RequestMapping("\${server.error.path:\${error.path:/error}}")
private class AppErrorController(errorAttributes: ErrorAttributes?) : AbstractErrorController(errorAttributes) {

    @RequestMapping
    fun error(request: HttpServletRequest): ResponseEntity<ErrorResponseDTO> {
        val status = getStatus(request)
        val errorAttributes = getErrorAttributes(
            request,
            ErrorAttributeOptions.defaults().including(*ErrorAttributeOptions.Include.entries.toTypedArray())
        )
        val errorResponse = ErrorResponseDTO(
            timestamp = Date(),
            status = status.value(),
            error = errorAttributes["message"] as String,
            path = errorAttributes["path"] as String
        )
        return ResponseEntity(errorResponse, status)
    }

    override fun getErrorAttributes(
        request: HttpServletRequest?,
        options: ErrorAttributeOptions?
    ): MutableMap<String, Any> {
        return super.getErrorAttributes(request, options)
    }

    override fun getTraceParameter(request: HttpServletRequest?): Boolean {
        return super.getTraceParameter(request)
    }

    override fun getMessageParameter(request: HttpServletRequest?): Boolean {
        return super.getMessageParameter(request)
    }

    override fun getErrorsParameter(request: HttpServletRequest?): Boolean {
        return super.getErrorsParameter(request)
    }

    override fun getPathParameter(request: HttpServletRequest?): Boolean {
        return super.getPathParameter(request)
    }

    override fun getBooleanParameter(request: HttpServletRequest?, parameterName: String?): Boolean {
        return super.getBooleanParameter(request, parameterName)
    }

    override fun getStatus(request: HttpServletRequest?): HttpStatus {
        return super.getStatus(request)
    }

    override fun resolveErrorView(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        status: HttpStatus?,
        model: MutableMap<String, Any>?
    ): ModelAndView {
        return super.resolveErrorView(request, response, status, model)
    }
}