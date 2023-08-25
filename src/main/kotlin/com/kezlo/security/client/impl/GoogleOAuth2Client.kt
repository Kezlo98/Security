package com.kezlo.security.client.impl

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import com.kezlo.security.client.OAuth2Client
import com.kezlo.security.model.OAuth2DTO
import com.kezlo.security.properties.OAuth2Properties
import org.springframework.context.annotation.Primary
import org.springframework.http.*
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate

@Service
@Primary
class GoogleOAuth2Client(
    val restTemplate: RestTemplate,
    val oAuth2Properties: OAuth2Properties
): OAuth2Client {

    override fun getToken(code: String): String {
        val body = createBodyForGetTokenAPI(code);
        val header = HttpHeaders()
        header.contentType = MediaType.APPLICATION_FORM_URLENCODED
        val request = HttpEntity<MultiValueMap<String, String>>(body,header);
        val response =
            restTemplate.postForEntity(oAuth2Properties.google.link.getToken, request, GoogleAccessToken::class.java)

        return response.body?.idToken ?: ""
//        return response.body?.accessToken ?: "";
    }

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class GoogleAccessToken(val accessToken: String?, val expiresIn: Int?, val refreshToken: String?, val scope: String?, val tokenType: String?, val idToken: String?)

    private fun createBodyForGetTokenAPI(code: String): MultiValueMap<String,String>{
        val body: MultiValueMap<String, String> = LinkedMultiValueMap()
        body.add("client_id",oAuth2Properties.google.client.id)
        body.add("client_secret",oAuth2Properties.google.client.secret)
        body.add("redirect_uri",oAuth2Properties.google.redirectUrl)
        body.add("grant_type","authorization_code")
        body.add("code",code)

        return body
    }

    override fun getUserInfo(token: String): OAuth2DTO?{
        val response =
            restTemplate.getForEntity(oAuth2Properties.google.link.getUserInfo + token, OAuth2DTO::class.java)

        return response.body
    }

    override fun verifyToken(token: String): Boolean{
        val response =
                restTemplate.getForEntity(oAuth2Properties.google.link.verifyToken + token, Any::class.java)

        return response.statusCode == HttpStatus.OK
    }

}