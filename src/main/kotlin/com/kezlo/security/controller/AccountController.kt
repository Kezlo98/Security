package com.kezlo.security.controller

import com.kezlo.security.entity.Accounts
import com.kezlo.security.repository.AccountsRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(
    val accountsRepository: AccountsRepository
) {

    @GetMapping("/myAccount")
    fun getAccountDetail(@RequestParam id: Int): Accounts? {
        return accountsRepository.findByCustomerId(id)
    }
}