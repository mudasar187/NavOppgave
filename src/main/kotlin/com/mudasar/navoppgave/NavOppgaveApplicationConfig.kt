package com.mudasar.navoppgave

import com.mudasar.navoppgave.util.NavConfigProperties
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate

@Configuration
class NavOppgaveApplicationConfig {

    @Bean
    fun navConfigProperties(): NavConfigProperties {
        return NavConfigProperties()
    }

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplateBuilder()
            .defaultHeader("Authorization", "Bearer " + navConfigProperties().apikey)
            .build()
    }
}