package com.mudasar.navoppgave

import com.fasterxml.jackson.databind.ObjectMapper
import com.mudasar.navoppgave.service.JobAdService
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import java.time.LocalDate

@SpringBootApplication
class NavOppgaveApplication

fun main(args: Array<String>) {
    val applicationContext = runApplication<NavOppgaveApplication>(*args)
    val jobAdService = applicationContext.getBean(JobAdService::class.java)
    val jobAdResult =
        jobAdService.findJobAds(LocalDate.now().minusMonths(6), LocalDate.now(), arrayListOf("Java", "Kotlin"))

    val mapper = ObjectMapper()

    val prettyJsonString = mapper.writerWithDefaultPrettyPrinter()
        .writeValueAsString(jobAdResult)

    println("\n" + prettyJsonString)
}
