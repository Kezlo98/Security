package com.kezlo.security.filter

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse

class AuthoritiesLoggingAtFilter : Filter {

    private val logger = KotlinLogging.logger {AuthoritiesLoggingAtFilter::class.java}
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {

        logger.info { "Authentication Validation is in progres" }


        chain?.doFilter(request, response)
    }
}