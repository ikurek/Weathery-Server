package com.ikurek.weathery

import com.ikurek.weathery.database.WeatherDataRepository
import com.ikurek.weathery.model.WeatherData
import com.samskivert.mustache.Mustache
import org.springframework.boot.Banner
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.util.*

@SpringBootApplication
class WeatheryApplication {

    @Bean
    fun mustacheCompiler(loader: Mustache.TemplateLoader?) =
            Mustache.compiler().escapeHTML(false).withLoader(loader)

    @Bean
    fun initDatabase(weatherDataRepository: WeatherDataRepository) = CommandLineRunner {
        //TODO: Init database
    }
}

fun main(args: Array<String>) {
    runApplication<WeatheryApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}