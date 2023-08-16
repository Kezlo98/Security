package com.kezlo.security.controller

import com.kezlo.security.entity.Contact
import com.kezlo.security.repository.ContactRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.*


@RestController
class ContactController(
    val contactRepository: ContactRepository
) {

    @GetMapping("/contact")
    fun saveContractInquiryDetails(): String {
        return "Inquiry details are saved to the DB"
    }

    @PostMapping("/contact")
    fun saveContactInquiryDetails(@RequestBody contact: Contact): Contact {
        contact.contactId = getServiceReqNumber()
        contact.createDt = Date(System.currentTimeMillis())
        return contactRepository.save(contact)
    }

    fun getServiceReqNumber(): String {
        val random = Random()
        val ranNum: Int = random.nextInt(999999999 - 9999) + 9999
        return "SR$ranNum"
    }
}