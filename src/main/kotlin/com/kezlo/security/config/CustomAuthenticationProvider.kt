package com.kezlo.security.config

import com.kezlo.security.constant.OAuth2TypeEnum
import com.kezlo.security.entity.Authority
import com.kezlo.security.model.AuthenticationDto
import com.kezlo.security.repository.CustomerRepository
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken
import org.springframework.stereotype.Component

//@Component
class CustomAuthenticationProvider(
    private val customerRepository: CustomerRepository,
    private val passwordEncoder: PasswordEncoder
): AuthenticationProvider {
    override fun authenticate(authentication: Authentication?): Authentication {
        val authenticationDto: AuthenticationDto = authentication?.principal as AuthenticationDto
        val loginType = authenticationDto.loginType
        return when (loginType){
            "BASIC"  -> authenticateBasicAuth(authenticationDto)
            else -> authenticateOAuth(authenticationDto)
        }

    }

    fun authenticateBasicAuth(authenticationDto: AuthenticationDto): UsernamePasswordAuthenticationToken{
        val customer = authenticationDto.let { customerRepository.findByEmail(it.username) }
        val pwd = authenticationDto.pwd
        if(customer.isNotEmpty()){
            if(passwordEncoder.matches(pwd,customer[0].pwd)){
                return UsernamePasswordAuthenticationToken(authenticationDto.username,pwd,getGrantedAuthorities(customer[0].authorities))
            }else {
                throw BadCredentialsException("Invalid Password!")
            }
        } else {
            throw BadCredentialsException("No user registered with this details!")
        }
    }

    fun authenticateOAuth(authenticationDto: AuthenticationDto) : UsernamePasswordAuthenticationToken{
        val email = authenticationDto.username
        val loginType = authenticationDto.loginType

        val customerOpt = customerRepository.findByEmailAndLoginType(email, loginType = loginType)

        if(customerOpt.isEmpty){
            throw BadCredentialsException("User not register with system, please sign up first !!")
        }

        return UsernamePasswordAuthenticationToken(authenticationDto.username,"",authenticationDto.authorities)
    }

    fun getGrantedAuthorities(authorities: Set<Authority>): List<GrantedAuthority>{
        val grantedAuthority = ArrayList<GrantedAuthority>()
        for (authority in authorities){
            grantedAuthority.add(SimpleGrantedAuthority(authority.name))
        }

        return grantedAuthority
    }

    override fun supports(authentication: Class<*>): Boolean {
        return UsernamePasswordAuthenticationToken::class.java.isAssignableFrom(authentication) ||
                OAuth2AuthenticationToken::class.java.isAssignableFrom(authentication)
    }
}