package com.mudasar.navoppgave.util

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "nav-service")
data class NavConfigProperties(
    var baseurl: String = "",
    var apikey: String = "",
    var adPath: String = ""
)