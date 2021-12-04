package com.example.authentication

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.auth.*
import io.ktor.auth.jwt.*

class JwtConfig(jwtSecret: String) {

    companion object Constants {
        // jwt config
        private const val jwtIssuer = "com.example"
        private const val jwtRealm = "com.example.mySchool"

        // claims
        private const val CLAIM_PASSWORD = "password"
        private const val CLAIM_USERNAME = "name"
    }

    private val jwtAlgorithm = Algorithm.HMAC512(jwtSecret)
    private val jwtVerifier: JWTVerifier = JWT
        .require(jwtAlgorithm)
        .withIssuer(jwtIssuer)
        .build()


    fun generateToken(user: JwtUser): String = JWT.create()
        .withSubject("Authentication")
        .withIssuer(jwtIssuer)
        .withClaim(CLAIM_PASSWORD, user.password)
        .withClaim(CLAIM_USERNAME, user.name)
        .sign(jwtAlgorithm)


    fun configureKtorFeature(config: JWTAuthenticationProvider.Configuration) = with(config) {
        verifier(jwtVerifier)
        realm = jwtRealm
        validate {
            val password = it.payload.getClaim(CLAIM_PASSWORD).asString()
            val name = it.payload.getClaim(CLAIM_USERNAME).asString()

            if (password != null && name != null) {
                JwtUser(name, password)
            } else {
                null
            }
        }
    }


    data class JwtUser( val name: String,val password: String): Principal

}