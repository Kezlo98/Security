package com.kezlo.security.constant

class OAuth2Constant {

    companion object{
//        Google OAuth2
        val GOOGLE_CLIENT_ID = "450982459846-die6mu5a8nn7d0go8c4invio7l3a5puu.apps.googleusercontent.com"
        val GOOGLE_CLIENT_SECRET = "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"
        val GOOGLE_TOKEN_URL = "https://accounts.google.com/o/oauth2/v2/auth"
        val GOOGLE_REDIRECT_URL = "http://localhost:8080/login/oauth2/code/google"

        val GITHUB_CLIENT_ID = "953e0f21abfa0c2eee61"
        val GITHUB_CLIENT_SECRET = "b1428a314b680096353ca6a2a47750f0c72328e8"
    }
}