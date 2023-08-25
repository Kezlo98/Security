package com.kezlo.security.controller

import com.kezlo.security.entity.Accounts
import com.kezlo.security.repository.AccountsRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class AccountController(
    val accountsRepository: AccountsRepository
) {

    @GetMapping("/myAccount")
    @PreAuthorize("hasRole('USER')")
    fun getAccountDetail(authentication: Authentication): Accounts? {
        var username = authentication.name;
        return accountsRepository.findByCustomerEmail(username)
    }
}