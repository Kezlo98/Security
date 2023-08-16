package com.kezlo.security.controller

import com.kezlo.security.entity.Cards
import com.kezlo.security.repository.CardsRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class CardsController(
    val cardsRepository: CardsRepository
) {

    @GetMapping("/myCards")
    fun getCardDetails(@RequestParam id: Int): List<Cards?>? {
        return cardsRepository.findByCustomerId(id)
    }
}
