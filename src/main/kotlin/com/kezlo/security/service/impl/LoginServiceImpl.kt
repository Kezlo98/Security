package com.kezlo.security.service.impl

import com.fasterxml.jackson.databind.ObjectMapper
import com.kezlo.security.client.OAuth2Client
import com.kezlo.security.client.impl.OAuth2ClientFactory
import com.kezlo.security.constant.OAuth2TypeEnum
import com.kezlo.security.entity.Customer
import com.kezlo.security.exception.BusinessException
import com.kezlo.security.exception.ErrorCode
import com.kezlo.security.model.AuthenticationDto
import com.kezlo.security.repository.CustomerRepository
import com.kezlo.security.service.LoginService
import com.kezlo.security.utils.JWTUtils
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class LoginServiceImpl(
    val customerRepository: CustomerRepository,
    val passwordEncoder: PasswordEncoder,
    val oAuth2ClientFactory: OAuth2ClientFactory,
    val objectMapper: ObjectMapper
): LoginService {

    override fun registerUser(customer: Customer): String {
        try {
            customer.pwd = passwordEncoder.encode(customer.pwd)
            val savedCustomer = customerRepository.save(customer)
            if (savedCustomer.id > 0) {
                return "Given user details are successfully registered"
            }
        } catch (ex: Exception) {
            throw BusinessException(ErrorCode.FAIL,ErrorCode.FAIL.message);
        }
        return "";
    }

    override fun registerOauth2User(code: String, oAuth2TypeEnum: OAuth2TypeEnum): String{
        val oAuth2Client = oAuth2ClientFactory.getOAuth2Client(oAuth2TypeEnum)
        val token = oAuth2Client.getToken(code)

        val claims = JWTUtils.getClaimFromJWT(token,objectMapper)
        val email = claims["email"] as String
        if(email.isEmpty()){
            throw BusinessException(ErrorCode.FAIL, "Error when login with ${oAuth2TypeEnum.name}")
        }

        val customerOpt = customerRepository.findByEmailAndLoginType(email, oAuth2TypeEnum.name)
        if(customerOpt.isEmpty){
            customerRepository.save(Customer(email = email, loginType = oAuth2TypeEnum.name))
        }

        return token
    }

}