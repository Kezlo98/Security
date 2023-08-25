package com.kezlo.security.repository

import com.kezlo.security.entity.Customer
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface CustomerRepository: JpaRepository<Customer, Long> {

    fun findByEmail(email: String): List<Customer>

    fun findByEmailAndLoginType(email: String, loginType: String): Optional<Customer>
}