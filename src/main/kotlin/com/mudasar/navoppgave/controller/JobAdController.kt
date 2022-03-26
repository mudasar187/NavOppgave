package com.mudasar.navoppgave.controller

import com.mudasar.navoppgave.service.JobAdService
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RequestMapping(
    path = ["/job/ad"],
    produces = [MediaType.APPLICATION_JSON_VALUE])
@RestController
class JobAdController(
    private val jobAdService: JobAdService
) {

    @GetMapping
    fun getJobAd(
        @RequestParam("from", defaultValue = "#{T(java.time.LocalDate).now().minusMonths(6)}", required = false) @DateTimeFormat(
            iso = DateTimeFormat.ISO.DATE) from: LocalDate,
        @RequestParam("to", defaultValue = "#{T(java.time.LocalDate).now()}", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: LocalDate,
        @RequestParam("keywords", defaultValue = "Java, Kotlin", required = false) keywords: List<String>
    ) : ResponseEntity<Any> {
        return ResponseEntity.ok(jobAdService.findJobAds(from, to, keywords))
    }
}