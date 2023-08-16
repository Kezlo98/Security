package com.kezlo.security.entity

import jakarta.persistence.*
import org.hibernate.annotations.GenericGenerator
import java.util.*

@Entity
@Table(name = "loans")
data class Notice(
    @Id @GeneratedValue(strategy = GenerationType.AUTO, generator = "native") @GenericGenerator(
        name = "native",
        strategy = "native"
    ) @Column(name = "notice_id") val noticeId: Int? = null,

    @Column(name = "notice_summary") val noticeSummary: String? = null,

    @Column(name = "notice_details") val noticeDetails: String? = null,

    @Column(name = "notic_beg_dt") val noticBegDt: Date? = null,

    @Column(name = "notic_end_dt") val noticEndDt: Date? = null,

    @Column(name = "create_dt") val createDt: Date? = null,

    @Column(name = "update_dt") val updateDt: Date? = null,
)