package com.kezlo.security.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import java.util.*

@Entity
data class Accounts(
    @Column(name = "customer_id")
    var customerId: Int? = null,

    @Id @Column(name = "account_number")
    var accountNumber: Long? = null,

    @Column(name = "account_type")
    var accountType: String? = null,

    @Column(name = "branch_address")
    var branchAddress: String? = null,

    @Column(name = "create_dt")
    var createDt: Date? = null
)