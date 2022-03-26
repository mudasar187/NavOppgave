package com.mudasar.navoppgave.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class DateParser {

    companion object {

        /**
         * Convert LocalDate to LocalDateTime
         */
        fun convertLocalDateToLocalDateTime(localDate: LocalDate): LocalDateTime {
            val time = LocalTime.now()
            return LocalDateTime.of(localDate, time).withNano(0)
        }

        /**
         * Fetch the week and make the out to -> "12-2022"
         */
        fun getWeekNumberAndYear(date: LocalDate): String {
            val yearWeek = DateTimeFormatter.ofPattern("ww-yyyy")
            return yearWeek.format(date)
        }

    }

}