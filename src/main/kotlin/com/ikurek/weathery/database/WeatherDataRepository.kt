package com.ikurek.weathery.database

import com.ikurek.weathery.model.WeatherData
import org.springframework.data.repository.CrudRepository

interface WeatherDataRepository: CrudRepository<WeatherData?, Long> {
    fun findFirstByOrderByCreatedAtDesc() : WeatherData?
}