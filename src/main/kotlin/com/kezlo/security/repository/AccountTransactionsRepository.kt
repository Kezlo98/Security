package com.kezlo.security.repository;

import com.kezlo.security.entity.AccountTransactions
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountTransactionsRepository : JpaRepository<AccountTransactions, String> {

    fun findByCustomerIdOrderByTransactionDtDesc(customerId: Int): List<AccountTransactions>
}