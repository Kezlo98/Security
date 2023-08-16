package com.kezlo.security.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class WelcomeController {

    @GetMapping("/welcome")
    fun sayWelcome(): String {
        return "Welcome to Spring Application without Security" ;
    }
}