package com.kezlo.security.controller

import com.kezlo.security.entity.Customer
import com.kezlo.security.repository.CustomerRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class LoginController(
    val customerRepository: CustomerRepository, val passwordEncoder: PasswordEncoder
) {

    @PostMapping("/register")
    fun registerUser(@RequestBody customer: Customer): ResponseEntity<String> {
        try {
            customer.pwd = passwordEncoder.encode(customer.pwd)
            var savedCustomer = customerRepository.save(customer)
            if (savedCustomer.id > 0) {
                return ResponseEntity(
                    "Given user details are successfully registered", HttpStatus.CREATED
                )
            }
        } catch (ex: Exception) {
            return ResponseEntity(
                "An exception occurred due to ${ex.message}", HttpStatus.INTERNAL_SERVER_ERROR
            )
        }

        return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @RequestMapping("/user")
    fun getUserDetailsAfterLogin(authentication: Authentication): Customer? {
        val customers = customerRepository.findByEmail(authentication.name)
        return if (customers.isNotEmpty()) customers[0] else null
    }

}