package com.kezlo.security.repository;

import com.kezlo.security.entity.Accounts
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountsRepository : JpaRepository<Accounts, Long> {

    fun findByCustomerId(customerId: Int): Accounts?
}