package com.mudasar.navoppgave.service

import com.mudasar.navoppgave.domain.JobAd
import com.mudasar.navoppgave.integration.NavClient
import com.mudasar.navoppgave.util.DateParser.Companion.convertLocalDateToLocalDateTime
import com.mudasar.navoppgave.util.DateParser.Companion.getWeekNumberAndYear
import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class JobAdService(
    private val navClient: NavClient
) {

    companion object {
        private const val PAGE_NUMBER: Int = 0
    }

    private val logger = LogManager.getLogger(JobAdService::class.java)

    /**
     * Find JobAds based on keywords
     * @param from
     * @param to
     * @param keywords
     * @return final result by type MutableMap<String, MutableMap<String, Int>>
     */
    fun findJobAds(
        from: LocalDate,
        to: LocalDate,
        keywords: List<String>
    ): MutableMap<String, MutableMap<String, Int>> {

        // Call the NavCLient to get response
        val response = navClient.getJobAds(convertLocalDateToLocalDateTime(from), to, PAGE_NUMBER)

        // Collect the content list
        val jobAdList = response.body?.content?.toMutableList()

        when {
            // If list is empty then return empty map
            jobAdList.isNullOrEmpty() -> {
                return mutableMapOf()
            }

            else -> {
                // Get total pages
                val totalPageNumber = response.body!!.totalPages

                // Check if totalPageNumber > 0, means we have more hits
                when {
                    totalPageNumber > 0 -> {
                        // Iterate from 1 because we have already fetched first page
                        (1..totalPageNumber).forEach { page ->
                            jobAdList += navClient.getJobAds(
                                convertLocalDateToLocalDateTime(from),
                                to,
                                page
                            ).body!!.content.toMutableList()
                        }
                    }
                }

                logger.info(
                    "SUCCESS fetching {} total pages and {} total elements",
                    response.body!!.totalPages,
                    response.body!!.totalElements
                )

                // Return the map after filtering by keyword list
                return findAdsByKeyWords(jobAdList, keywords)
            }
        }
    }

    /**
     * Filtering the list by keywords
     * @param jobAds
     * @param keywords
     * @return filtering results by type MutableMap<String, MutableMap<String, Int>>
     */
    private fun findAdsByKeyWords(
        jobAds: MutableList<JobAd>,
        keywords: List<String>
    ): MutableMap<String, MutableMap<String, Int>> {

        val resultMap = mutableMapOf<String, MutableMap<String, Int>>()
        val counter = mutableMapOf<String, Int>() // To get total count of the keywords

        // Create Regex for each word in keyword list to use it for searching in JobAd object
        val keywordSearch = keywords.map { it to """(?i)\b($it)\b""".toRegex() }

        jobAds.forEach { jobAd: JobAd ->

            // Fetch the weeknumber
            val weekNumber = getWeekNumberAndYear(jobAd.published)

            // Initialize placeholder data, to fill even empty weeks
            resultMap.putIfAbsent(weekNumber, emptyMapOfKeywords(keywords))

            keywordSearch.forEach { (keywordString, keywordRegex) ->
                when {
                    checkJobAdForKeyWord(jobAd, keywordRegex) -> {
                        // If the key wasn't already in the map, it sets the value to 1.
                        // If the key was already in the map, it combines the previous value with the new value (1) using the given merge function
                        // Here Int::plus, which is a short-hand for { old, new -> old + new }.
                        counter.merge(keywordString, 1, Int::plus)
                        resultMap[weekNumber]!!.merge(keywordString, 1, Int::plus)
                    }
                }
            }
        }

        resultMap["total"] = counter
        logger.info("Found {} matches", counter)
        return resultMap
    }

    /**
     * Placeholder data
     * @param keywords
     * @return keywordMap by type MutableMap<String, Int>
     */
    private fun emptyMapOfKeywords(keywords: List<String>): MutableMap<String, Int> {
        val keywordMap = mutableMapOf<String, Int>()
        keywords.forEach { keyword ->
            keywordMap[keyword] = 0
        }
        return keywordMap
    }

    /**
     * Find keyword by Regex
     * @param jobAd
     * @param keywordRegex
     * @return true/false by type Boolean
     */
    private fun checkJobAdForKeyWord(jobAd: JobAd, keywordRegex: Regex): Boolean {
        return keywordRegex.containsMatchIn(jobAd.title.toString()) || keywordRegex.containsMatchIn(jobAd.jobtitle.toString()) || keywordRegex.containsMatchIn(
            jobAd.description.toString()
        )
    }
}