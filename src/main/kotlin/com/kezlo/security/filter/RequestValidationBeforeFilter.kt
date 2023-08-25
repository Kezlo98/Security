package com.kezlo.security.filter

import com.kezlo.security.client.OAuth2Client
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.util.StringUtils
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets
import java.util.*

@Component
class RequestValidationBeforeFilter(
    oAuth2Client: OAuth2Client
) {

    private val AUTHENTICATION_SCHEME_BABIC = "Basic"
    private val AUTHENTICATION_SCHEME_BEARER = "Bearer"
    private val credentialsCharset: Charset = StandardCharsets.UTF_8
    fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val req = request as HttpServletRequest
        val res = response as HttpServletResponse
        var header = req.getHeader(AUTHORIZATION)
        if (header == null) {
            chain.doFilter(request, response)
            return
        }
        header = header.trim()

        if (StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BABIC)) {
            validateBasicAuthRequest(header, res)
        } else if (StringUtils.startsWithIgnoreCase(header,AUTHENTICATION_SCHEME_BEARER)){

        }

//        val auth = UsernamePasswordAuthenticationToken(
//            username,null, AuthorityUtils.commaSeparatedStringToAuthorityList(authorities))
//        SecurityContextHolder.getContext().authentication = auth

        chain.doFilter(request, response)
    }

    private fun validateBasicAuthRequest(header: String, res: HttpServletResponse){
        val base64Token = header.substring(6).toByteArray(StandardCharsets.UTF_8)
        val decoded: ByteArray
        try {
            decoded = Base64.getDecoder().decode(base64Token)
            val token = String(decoded, credentialsCharset)
            val delim = token.indexOf(":")
            if (delim == -1) {
                throw BadCredentialsException("Invalid basic authentication token")
            }
            val email = token.substring(0, delim)
            if (email.lowercase().contains("test")) {
                res.status = HttpServletResponse.SC_BAD_REQUEST
            }
        } catch (ex: IllegalArgumentException) {
            throw BadCredentialsException("Failed to decode basic authentication token")
        }
    }

    private fun validateOAuth2Request(header: String, res: HttpServletResponse){
        val token = header.substring(7);

    }
}