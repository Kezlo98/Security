package com.kezlo.security.filter

import com.kezlo.security.constant.SecurityConstant
import com.kezlo.security.model.AuthenticationDto
import com.kezlo.security.utils.JWTUtils
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter

class JWTTokenGeneratorFilter: OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorization = SecurityContextHolder.getContext().authentication;
        if (authorization == null){
            filterChain.doFilter(request,response)
            return
        }

        val jwt = JWTUtils.generateJWT(AuthenticationDto(authorization.name , authorization.credentials.toString(), authorization.authorities ,"BASIC"))

        response.setHeader(SecurityConstant.JWT_HEADER, jwt)
        filterChain.doFilter(request,response)
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return !shouldFilter(request)
    }

    private fun shouldFilter(request: HttpServletRequest): Boolean {
        return request.servletPath.equals("/user")
    }

    private fun populateAuthorities(authorities: Collection<GrantedAuthority>): String {

        return authorities.joinToString(",")
    }
}