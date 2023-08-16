package com.kezlo.security.repository;

import com.kezlo.security.entity.Cards
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository


@Repository
interface CardsRepository : JpaRepository<Cards, Int> {

    fun findByCustomerId(customerId: Int): List<Cards?>?
}