package com.kezlo.security.controller

import com.kezlo.security.entity.Loans
import com.kezlo.security.repository.LoansRepository
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController


@RestController
class LoansController(
    val  loansRepository: LoansRepository
) {

    @GetMapping("/myLoans")
    @PreAuthorize("hasRole('USER')")
    fun getLoanDetails(@RequestParam id: Int): List<Loans?>? {
        return loansRepository.findByCustomerIdOrderByStartDtDesc(id)
    }
}