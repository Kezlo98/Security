package com.kezlo.security.entity

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.util.*

@Entity
@Table(name = "cards")
data class Cards(
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") @GenericGenerator(
        name = "native",
        strategy = "native"
    ) @Column(name = "card_id") var cardId: Int? = null,

    @Column(name = "customer_id") var customerId: Int? = null,

    @Column(name = "card_number") var cardNumber: String? = null,

    @Column(name = "card_type") var cardType: String? = null,

    @Column(name = "total_limit") var totalLimit: Int? = null,

    @Column(name = "amount_used") var amountUsed: Int? = null,

    @Column(name = "available_amount") var availableAmount: Int? = null,

    @Column(name = "create_dt") var createDt: Date? = null
)