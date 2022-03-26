package com.mudasar.navoppgave.util.exception

import java.time.LocalDate

class JobAdErrorResponse(
    var status: Int,
    var message: String?,
    var timeStamp: LocalDate
)