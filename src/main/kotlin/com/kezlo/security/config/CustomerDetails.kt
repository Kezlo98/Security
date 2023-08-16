package com.kezlo.security.config

import com.kezlo.security.repository.CustomerRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

// No need
// Because we create our own Provider => No need to use DaoAuthenticationProvider
// => No need to override UserDetailService
//@Service
class CustomerDetails(
    val customerRepository: CustomerRepository
): UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {

        val customers = customerRepository.findByEmail(username)
        val authorities = ArrayList<GrantedAuthority>()
        val password: String?

        if (customers.isEmpty()){
            throw UsernameNotFoundException("User details not found for the user: $username")
        } else {
            password = customers[0].pwd
            authorities.add(SimpleGrantedAuthority(customers[0].role))
        }

        return User(username, password, authorities)
    }
}