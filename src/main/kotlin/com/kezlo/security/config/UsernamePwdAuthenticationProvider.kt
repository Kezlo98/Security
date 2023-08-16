package com.kezlo.security.config

import com.kezlo.security.repository.CustomerRepository
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class UsernamePwdAuthenticationProvider(
    private val customerRepository: CustomerRepository,
    private val passwordEncoder: PasswordEncoder
): AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication {
        val customer = authentication?.let { customerRepository.findByEmail(it.name) }
        val pwd = authentication?.credentials?.toString()
        if(!customer.isNullOrEmpty()){
            if(passwordEncoder.matches(pwd,customer[0].pwd)){
                val authorities = ArrayList<GrantedAuthority>()
                authorities.add(SimpleGrantedAuthority(customer[0].role))
                return UsernamePasswordAuthenticationToken(authentication.name,pwd,authorities)
            }else {
                throw BadCredentialsException("Invalid Password!")
            }
        } else {
            throw BadCredentialsException("No user registered with this details!")
        }

    }

    override fun supports(authentication: Class<*>): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}