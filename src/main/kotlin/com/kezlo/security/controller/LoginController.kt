package com.kezlo.security.controller

import com.kezlo.security.constant.OAuth2TypeEnum
import com.kezlo.security.entity.Customer
import com.kezlo.security.repository.CustomerRepository
import com.kezlo.security.service.LoginService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.security.Principal


@RestController
class LoginController(
    val customerRepository: CustomerRepository,
    val passwordEncoder: PasswordEncoder,
    val loginService: LoginService
) {

    @PostMapping("/register")
    fun registerUser(@RequestBody customer: Customer): ResponseEntity<String> {
        val result = loginService.registerUser(customer)
        if(result.isEmpty()){
            return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
        }

        return ResponseEntity(result,HttpStatus.OK)
    }

    @RequestMapping("/user")
    fun getUserDetailsAfterLogin(authentication: Authentication): Customer? {
        val customers = customerRepository.findByEmail(authentication.name)
        return if (customers.isNotEmpty()) customers[0] else null
    }

//    @PostMapping("/oauth2/{type}/login")
//    fun loginWithOAuth2(@RequestBody type: OAuth2TypeEnum): ResponseEntity<String>{
//
//    }

    @GetMapping("/login/oauth2/code/{oauth2Type}")
    fun loginWithOAuth2(@PathVariable("oauth2Type") oAuth2TypeEnum: OAuth2TypeEnum ,@RequestParam(value = "code") token: String?): String {
        return loginService.registerOauth2User(code = token ?: "", oAuth2TypeEnum = oAuth2TypeEnum);
    }

}