package com.luizmatias.workout_tracker.config.exception.controller

import com.luizmatias.workout_tracker.features.common.dto.ErrorResponseDTO
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController
import org.springframework.boot.web.error.ErrorAttributeOptions
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("\${server.error.path:\${error.path:/error}}")
class AppErrorController(
    errorAttributes: ErrorAttributes?,
    @Value("\${server.http-response.should-show-internal-errors:false}")
    private val shouldShowInternalErrors: Boolean,
) : AbstractErrorController(errorAttributes) {
    @RequestMapping
    fun error(request: HttpServletRequest): ResponseEntity<ErrorResponseDTO> {
        val status = getStatus(request)
        val errorAttributes =
            getErrorAttributes(
                request,
                ErrorAttributeOptions.defaults().including(*ErrorAttributeOptions.Include.entries.toTypedArray()),
            )

        if (status == HttpStatus.INTERNAL_SERVER_ERROR && !shouldShowInternalErrors) {
            errorAttributes["message"] = "An internal server error has occurred."
        }

        val errorResponse =
            ErrorResponseDTO(
                status = status.value(),
                error = errorAttributes["message"] as String,
                path = errorAttributes["path"] as String,
            )
        return ResponseEntity(errorResponse, status)
    }

    override fun getErrorAttributes(
        request: HttpServletRequest?,
        options: ErrorAttributeOptions?,
    ): MutableMap<String, Any> = super.getErrorAttributes(request, options)

    override fun getTraceParameter(request: HttpServletRequest?): Boolean = super.getTraceParameter(request)

    override fun getMessageParameter(request: HttpServletRequest?): Boolean = super.getMessageParameter(request)

    override fun getErrorsParameter(request: HttpServletRequest?): Boolean = super.getErrorsParameter(request)

    override fun getPathParameter(request: HttpServletRequest?): Boolean = super.getPathParameter(request)

    override fun getBooleanParameter(
        request: HttpServletRequest?,
        parameterName: String?,
    ): Boolean = super.getBooleanParameter(request, parameterName)

    override fun getStatus(request: HttpServletRequest?): HttpStatus = super.getStatus(request)

    override fun resolveErrorView(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        status: HttpStatus?,
        model: MutableMap<String, Any>?,
    ): ModelAndView = super.resolveErrorView(request, response, status, model)
}
