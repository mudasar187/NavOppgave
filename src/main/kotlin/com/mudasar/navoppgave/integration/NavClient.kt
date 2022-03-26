package com.mudasar.navoppgave.integration

import com.mudasar.navoppgave.domain.JobAdWrapper
import com.mudasar.navoppgave.util.NavConfigProperties
import com.mudasar.navoppgave.util.exception.JobAdErrorException
import org.apache.logging.log4j.LogManager
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.net.URI
import java.time.LocalDate
import java.time.LocalDateTime


@Component
class NavClient(
    private val navConfigProperties: NavConfigProperties,
    private val restTemplate: RestTemplate
) {

    companion object {
        private const val CATEGORY = "IT"
        private const val PAGE_SIZE = 100
    }

    private val logger = LogManager.getLogger(NavClient::class.java)

    /**
     * Fetch Job Ads from Nav
     */
    fun getJobAds(from: LocalDateTime, to: LocalDate, pageNumber: Int): ResponseEntity<JobAdWrapper> {

        // Create URI before call the API
        val uri = uriBuilder(from, to, pageNumber)

        return try {
            restTemplate.exchange(uri, HttpMethod.GET, null, object : ParameterizedTypeReference<JobAdWrapper>() {})
        } catch (e: HttpClientErrorException) {
            // Any kind of HttpClientError will be trown here
            logger.error("FAILED fetching JobAds from date: {} to date: {}", from, to)
            throw JobAdErrorException("Something went wrong, try again")
        } catch (e: Exception) {
            // Other exception will be thrown here
            throw JobAdErrorException("Something went wrong, try again")
        }
    }

    /**
     * Build URI
     */
    private fun uriBuilder(from: LocalDateTime, to: LocalDate, pageNumber: Int): URI {
        return UriComponentsBuilder
            .fromHttpUrl(navConfigProperties.baseurl)
            .path(navConfigProperties.adPath)
            .queryParam("category", CATEGORY)
            .queryParam("page", pageNumber)
            .queryParam("published", "[${from},${to}]")
            .queryParam("size", PAGE_SIZE)
            .build()
            .toUri()
    }
}