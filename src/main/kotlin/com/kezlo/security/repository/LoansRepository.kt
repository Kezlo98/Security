package com.kezlo.security.repository;

import com.kezlo.security.entity.Loans
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface LoansRepository : JpaRepository<Loans, Int> {

    fun findByCustomerIdOrderByStartDtDesc(customerId: Int): List<Loans?>?

}