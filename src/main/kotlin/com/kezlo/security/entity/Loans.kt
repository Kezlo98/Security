package com.kezlo.security.entity

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.util.*

@Entity
@Table(name = "loans")
data class Loans(
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") @GenericGenerator(
        name = "native",
        strategy = "native"
    ) @Column(name = "loan_number") val loanNumber: Int? = null,

    @Column(name = "customer_id") val customerId: Int? = null,

    @Column(name = "start_dt") val startDt: Date? = null,

    @Column(name = "loan_type") val loanType: String? = null,

    @Column(name = "total_loan") val totalLoan: Int? = null,

    @Column(name = "amount_paid") val amountPaid: Int? = null,

    @Column(name = "outstanding_amount") val outstandingAmount: Int? = null,

    @Column(name = "create_dt") val createDt: String? = null,
)