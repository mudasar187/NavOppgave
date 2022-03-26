package com.mudasar.navoppgave

import com.mudasar.navoppgave.util.DateParser.Companion.convertLocalDateToLocalDateTime
import com.mudasar.navoppgave.util.DateParser.Companion.getWeekNumberAndYear
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.LocalDate
import java.time.LocalTime

class DateParserTest {

    @Test
    fun `test get WeekNumber And Year`() {
        val localDate = LocalDate.of(2022, 3, 25)
        val formattedYearAndWeekString = getWeekNumberAndYear(localDate)
        assertEquals("12-2022", formattedYearAndWeekString)
    }

    @Test
    fun `test Convert LocalDate To LocalDateTime`() {
        val date = LocalDate.of(2022, 3, 24)
        val time = LocalTime.now().withNano(0)
        val formatted = convertLocalDateToLocalDateTime(date)

        assertEquals("2022-03-24T$time", formatted.toString())
    }

}