package com.mudasar.navoppgave

import com.mudasar.navoppgave.domain.JobAd
import com.mudasar.navoppgave.domain.JobAdWrapper
import com.mudasar.navoppgave.integration.NavClient
import com.mudasar.navoppgave.service.JobAdService
import com.mudasar.navoppgave.util.DateParser.Companion.convertLocalDateToLocalDateTime
import com.mudasar.navoppgave.util.DateParser.Companion.getWeekNumberAndYear
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.springframework.http.ResponseEntity
import java.time.LocalDate

class JobAdServiceTest {

    private val mockNavClient = mock(NavClient::class.java)
    private val jobAdService = JobAdService(mockNavClient)
    private val localDate = LocalDate.now()

    @BeforeEach
    fun setup() {

        val data = listOf(
            JobAd(
                published = localDate,
                title = "Frontendutvikler",
                description = "Søker en frontendutvikler som er glad i React eller Angular",
                jobtitle = "Senior frontendutvikler",
            ),
            JobAd(
                published = localDate,
                title = "AI utvikler",
                description = "Vi søker etter utvikler med Python kompetanse",
                jobtitle = "AI utvikler",
            ),
            JobAd(
                published = localDate,
                title = "Java Backend utvikler",
                description = "Vi søker etter utvikler med 5 års erfaring",
                jobtitle = "Backend utvikler",
            ),
            JobAd(
                published = localDate,
                title = "Backend utvikler",
                description = "Vi søker etter utvikler med Kotlin erfaring",
                jobtitle = "Kotlin utvikler",
            ),
            JobAd(
                published = localDate,
                title = "Backend utvikler",
                description = "Vi søker etter utvikler med Kotlin erfaring",
                jobtitle = "Backend utvikler",
            )
        )
        val jobAdWrapper = JobAdWrapper(
            data,
            5,
            0,
            10,
            0
        )
        `when`(mockNavClient.getJobAds(convertLocalDateToLocalDateTime(localDate), localDate, 0)).thenReturn(
            ResponseEntity.ok(
                jobAdWrapper
            )
        )
    }

    @Test
    fun findJobAdsTest() {
        val result = jobAdService.findJobAds(localDate, localDate, listOf("JAVA", "KOTLIN"))
        val week = getWeekNumberAndYear(localDate)
        Assertions.assertEquals(result[week]!!["JAVA"], 1)
        Assertions.assertEquals(result[week]!!["KOTLIN"], 2)
    }



}