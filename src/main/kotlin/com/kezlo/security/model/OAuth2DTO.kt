package com.kezlo.security.model

data class OAuth2DTO(
    val id: String? = null,
    val email: String = "",
    val verifiedEmail: Boolean = false,
    val name: String? = null,
    val givenName: String? = null,
    val familyName: String? = null,
    val link: String? = null,
    val picture: String? = null,
) {
}