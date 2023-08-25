package com.kezlo.security.constant

enum class OAuth2TypeEnum {
    GOOGLE, GITHUB, FACEBOOK;

    companion object {

        fun getTypeByIss(iss: String): OAuth2TypeEnum{
            return if (iss.contains("github")){
                GITHUB
            } else if (iss.contains("facebook")){
                FACEBOOK
            } else {
                GOOGLE
            }
        }
    }
}