package com.kezlo.security.controller

import com.kezlo.security.entity.AccountTransactions
import com.kezlo.security.repository.AccountTransactionsRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class BalanceController(
    val accountTransactionsRepository: AccountTransactionsRepository
) {

    @GetMapping("/myBalance")
    fun getBalanceDetails(@RequestParam id: Int): List<AccountTransactions> {
        return accountTransactionsRepository.findByCustomerIdOrderByTransactionDtDesc(id)
    }
}