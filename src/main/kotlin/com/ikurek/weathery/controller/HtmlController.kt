package com.ikurek.weathery.controller

import com.ikurek.weathery.database.WeatherDataRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.transaction.annotation.Transactional
import org.springframework.ui.Model
import org.springframework.ui.set
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HtmlController(
        @Autowired
        val weatherDataRepository: WeatherDataRepository
) {

    @Transactional
    @GetMapping("/")
    fun index(model: Model): String {
        model["title"] = "Weathery"

        // Read latest info from Log
        val logModel = weatherDataRepository.findFirstByOrderByCreatedAtDesc()

            model["temperature"] = logModel!!.temperature.toString()
            model["humidity"] = logModel!!.humidity.toString()
            model["pressure"] = logModel!!.pressure.toString()
            model["createdAt"] = logModel!!.createdAt.toString()
            model["savedAt"] = logModel!!.savedAt.toString()


        return "index"
    }

    @Transactional
    @GetMapping("/history")
    fun history(model: Model) : String {
        model["title"] = "History"

        //TODO: Do stuff

        return "history"
    }
}