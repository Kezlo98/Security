package com.kezlo.security.client.impl

import com.kezlo.security.client.OAuth2Client
import com.kezlo.security.constant.OAuth2TypeEnum
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class OAuth2ClientFactory(
    @Qualifier("googleOAuth2Client") val googleOAuth2Client: GoogleOAuth2Client
) {

    fun getOAuth2Client(oAuth2TypeEnum: OAuth2TypeEnum): OAuth2Client{
        return when (oAuth2TypeEnum){
            OAuth2TypeEnum.GOOGLE -> googleOAuth2Client
            else -> googleOAuth2Client
        }
    }
}