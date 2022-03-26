package com.mudasar.navoppgave.util.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDate

@ControllerAdvice
class JobAdException {

    @ExceptionHandler
    fun handleGenericErrorException(e: JobAdErrorException): ResponseEntity<JobAdErrorResponse> {
        val jobAdErrorResponse = JobAdErrorResponse(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            e.message,
            LocalDate.now()
        )

        return ResponseEntity(jobAdErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}