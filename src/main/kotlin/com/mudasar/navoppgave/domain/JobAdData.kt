package com.mudasar.navoppgave.domain

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import java.time.LocalDate

@JsonIgnoreProperties(ignoreUnknown = true)
data class JobAdWrapper(
    val content: List<JobAd>,
    val totalElements: Int,
    val pageNumber: Int = 0,
    val pageSize: Int,
    val totalPages: Int
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class JobAd(
    val published: LocalDate,
    val title: String?,
    val jobtitle: String?,
    val description: String?
)
