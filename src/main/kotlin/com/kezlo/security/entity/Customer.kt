package com.kezlo.security.entity

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.GenericGenerator

@Entity
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    val id: Long = 0,
    val name: String = "",
    val email: String= "",
    val mobileNumber: String? = "",
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) var pwd: String? = null,
    val role: String = "customer",
    val createDt: String? = ""
)