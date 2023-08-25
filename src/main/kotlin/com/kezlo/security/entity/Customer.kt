package com.kezlo.security.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.time.LocalDateTime

@Entity
data class Customer(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "customer_id")
    val id: Long = 0,
    val name: String = "",
    val email: String= "",
    val mobileNumber: String? = "",
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) var pwd: String? = null,
    val role: String = "customer",
    val createDt: LocalDateTime = LocalDateTime.now(),
    @JsonIgnore
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER)
    var authorities: Set<Authority> = setOf(),
    @Column(name = "login_type")
    var loginType: String = "basic"
)