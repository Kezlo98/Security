package com.kezlo.security.controller

import com.kezlo.security.entity.Contact
import com.kezlo.security.repository.ContactRepository
import org.springframework.security.access.prepost.PostFilter
import org.springframework.security.access.prepost.PreFilter
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
//    @PreFilter("filterObject.contactName != 'Test'")
    @PostFilter("filterObject.contactName != 'Test'")
    fun saveContactInquiryDetails(@RequestBody contacts: ArrayList<Contact>): ArrayList<Contact> {
        var result = arrayListOf<Contact>();
        for (contact in contacts){
            contact.contactId = getServiceReqNumber()
            contact.createDt = Date(System.currentTimeMillis())
            result.add(contactRepository.save(contact))
        }
        return result
    }

    fun getServiceReqNumber(): String {
        val random = Random()
        val ranNum: Int = random.nextInt(999999999 - 9999) + 9999
        return "SR$ranNum"
    }
}