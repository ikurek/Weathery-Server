package com.ikurek.weathery.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.ikurek.weathery.database.WeatherDataRepository
import com.ikurek.weathery.model.WeatherData
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

@RestController
@RequestMapping("/api")
class ApiController(
        @Autowired
        val weatherDataRepository: WeatherDataRepository
) {

    val objectMapper = ObjectMapper().apply {
        dateFormat = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
    }

    @Transactional
    @GetMapping("/current")
    fun getCurrent() : ResponseEntity<String> {

        try {
            weatherDataRepository.findFirstByOrderByCreatedAtDesc()?.let {
                return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(it))
            }
        } catch (exception: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.message)
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
    }

    @Transactional
    @GetMapping("/all")
    fun getAll(): ResponseEntity<String> {
        try {
            weatherDataRepository.findAll().let {
                return ResponseEntity.status(HttpStatus.OK).body(objectMapper.writeValueAsString(it))
            }
        } catch (exception: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.message)
        }
    }

    @Transactional
    @PostMapping("/new")
    fun createLog(@RequestBody postContent: String): ResponseEntity<String> {

        var newWeatherData: WeatherData? = null

        try {
            newWeatherData = objectMapper.readValue<WeatherData>(postContent).apply {
                savedAt = Date()
            }
        } catch (exception: Exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.message)
        }

        try {
            weatherDataRepository.save(newWeatherData).let {
                return ResponseEntity.status(HttpStatus.CREATED).body(objectMapper.writeValueAsString(it))
            }
        } catch (exception: Exception) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.message)
        }

    }

}