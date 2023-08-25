package com.kezlo.security.entity

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator

@Entity
@Table(name = "authorities")
class Authority(
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private var id: Long? = null,

    val name: String? = null,

    @ManyToOne @JoinColumn(name = "customer_id")
    private val customer: Customer? = null,

    ) {}