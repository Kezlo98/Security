package com.kezlo.security.repository;

import com.kezlo.security.entity.Notice
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository


@Repository
interface NoticeRepository : JpaRepository<Notice, Int> {

    @Query(value = "from Notice n where CURRENT_DATE() BETWEEN n.noticBegDt AND n.noticEndDt")
    fun findAllActiveNotices(): List<Notice?>?

}