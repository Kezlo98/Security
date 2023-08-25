package com.kezlo.security.client

import com.kezlo.security.model.OAuth2DTO

interface OAuth2Client {

    fun getToken(code: String): String
    fun getUserInfo(token: String): OAuth2DTO?
    fun verifyToken(token: String): Boolean
}