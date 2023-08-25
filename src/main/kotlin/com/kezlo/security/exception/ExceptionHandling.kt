package com.kezlo.security.exception

import com.kezlo.security.filter.AuthoritiesLoggingAtFilter
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class ExceptionHandling {

    private val logger = KotlinLogging.logger { ExceptionHandling::class.java}

    @ExceptionHandler
    fun handleException(ex: Exception): ResponseEntity<BusinessException> {

        val errorMessage = BusinessException(
            ErrorCode.FAIL,
            ErrorCode.FAIL.message
        )

        logger.error { "Error, ${ex.stackTraceToString()}" }

        return ResponseEntity(errorMessage, HttpStatus.BAD_REQUEST)
    }
}