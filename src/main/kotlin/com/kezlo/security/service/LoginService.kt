package com.kezlo.security.service

import com.kezlo.security.constant.OAuth2TypeEnum
import com.kezlo.security.entity.Customer

interface LoginService {

    fun registerUser(customer: Customer): String
    fun registerOauth2User(code: String, oAuth2TypeEnum: OAuth2TypeEnum): String
}