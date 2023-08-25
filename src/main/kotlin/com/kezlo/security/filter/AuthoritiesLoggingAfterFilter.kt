package com.kezlo.security.filter

import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.Filter
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import org.springframework.security.core.context.SecurityContextHolder

class AuthoritiesLoggingAfterFilter: Filter {

    private val logger = KotlinLogging.logger{}
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        var authentication = SecurityContextHolder.getContext().authentication;
        if(null != authentication){
            logger.info { "User ${authentication.name} is successfully authenticated " +
                    "and has the authorities ${authentication.authorities}" }
        }

        chain?.doFilter(request, response)
    }
}