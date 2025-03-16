package com.luizmatias.workout_tracker.config.exception.advice

import com.luizmatias.workout_tracker.config.exception.common_exceptions.BusinessRuleConflictException
import com.luizmatias.workout_tracker.config.exception.common_exceptions.ForbiddenException
import com.luizmatias.workout_tracker.config.exception.common_exceptions.InternalServerErrorException
import com.luizmatias.workout_tracker.config.exception.common_exceptions.MalformedDataException
import com.luizmatias.workout_tracker.config.exception.common_exceptions.NotFoundException
import com.luizmatias.workout_tracker.config.exception.common_exceptions.UnauthorizedException
import com.luizmatias.workout_tracker.features.common.dto.ErrorResponseDTO
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageConversionException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandlerAdvice {
    @Value("\${server.http-response.should-show-internal-errors:false}")
    private val shouldShowInternalErrors: Boolean = false

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(InternalServerErrorException::class)
    fun handleExplicitInternalServerErrorException(
        exception: InternalServerErrorException,
        request: HttpServletRequest,
    ): ErrorResponseDTO =
        ErrorResponseDTO(
            status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
            error = exception.message,
            path = request.requestURI,
        )

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(
        BusinessRuleConflictException::class,
    )
    fun handleBusinessRuleConflictException(
        exception: BusinessRuleConflictException,
        request: HttpServletRequest,
    ): ErrorResponseDTO =
        ErrorResponseDTO(
            status = HttpStatus.CONFLICT.value(),
            error = exception.message,
            path = request.requestURI,
        )

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException::class)
    fun handleForbiddenException(
        exception: ForbiddenException,
        request: HttpServletRequest,
    ): ErrorResponseDTO =
        ErrorResponseDTO(
            status = HttpStatus.FORBIDDEN.value(),
            error = exception.message,
            path = request.requestURI,
        )

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(
        MethodArgumentNotValidException::class,
    )
    fun handleInvalidArguments(
        exception: MethodArgumentNotValidException,
        request: HttpServletRequest,
    ): ErrorResponseDTO {
        val errorMap: MutableMap<String, String?> = HashMap()
        exception.bindingResult.fieldErrors.forEach {
            errorMap[it.field] = it.defaultMessage
        }

        return ErrorResponseDTO(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Malformed data",
            path = request.requestURI,
            extras = errorMap,
        )
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MalformedDataException::class)
    fun handleMalformedDataException(
        exception: MalformedDataException,
        request: HttpServletRequest,
    ): ErrorResponseDTO =
        ErrorResponseDTO(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Malformed data" + if (shouldShowInternalErrors) ": ${exception.message}" else "",
            path = request.requestURI,
        )

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageConversionException::class)
    fun handleHttpMessageConversionException(
        exception: HttpMessageConversionException,
        request: HttpServletRequest,
    ): ErrorResponseDTO =
        ErrorResponseDTO(
            status = HttpStatus.BAD_REQUEST.value(),
            error = "Malformed data" + if (shouldShowInternalErrors) ": ${exception.message}" else "",
            path = request.requestURI,
        )

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(
        exception: NotFoundException,
        request: HttpServletRequest,
    ): ErrorResponseDTO =
        ErrorResponseDTO(
            status = HttpStatus.NOT_FOUND.value(),
            error = exception.message,
            path = request.requestURI,
        )

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorizedException(
        exception: UnauthorizedException,
        request: HttpServletRequest,
    ): ErrorResponseDTO =
        ErrorResponseDTO(
            status = HttpStatus.UNAUTHORIZED.value(),
            error = exception.message,
            path = request.requestURI,
        )
}
