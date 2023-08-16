package com.kezlo.security.controller

import com.kezlo.security.entity.Notice
import com.kezlo.security.repository.NoticeRepository
import org.springframework.http.CacheControl
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.TimeUnit


@RestController
class NoticesController(
    val noticeRepository: NoticeRepository
) {

    @GetMapping("/notices")
    fun getNotices(): ResponseEntity<List<Notice?>>? {
        val notices = noticeRepository.findAllActiveNotices()
        return if (notices != null) {
            ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(60, TimeUnit.SECONDS))
                .body(notices)
        } else {
            null
        }
    }
}