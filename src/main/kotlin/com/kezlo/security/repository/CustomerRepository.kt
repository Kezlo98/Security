package com.kezlo.security.repository

import com.kezlo.security.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerInterface: JpaRepository<Customer, Long> {

    fun findByEmail(email: String): List<Customer>
}