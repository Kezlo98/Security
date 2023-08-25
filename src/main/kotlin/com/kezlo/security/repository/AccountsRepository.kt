package com.kezlo.security.repository;

import com.kezlo.security.entity.Accounts
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface AccountsRepository : JpaRepository<Accounts, Long> {

    fun findByCustomerId(customerId: Int): Accounts?

    @Query("SELECT acc FROM Accounts acc " +
            "LEFT JOIN Customer cus ON acc.customerId = cus.id WHERE cus.name = :name ")
    fun findByCustomerName(@Param("name") customerName: String): Accounts?

    @Query("SELECT acc FROM Accounts acc " +
            "LEFT JOIN Customer cus ON acc.customerId = cus.id WHERE cus.email = :email ")
    fun findByCustomerEmail(@Param("email") email: String): Accounts?
}