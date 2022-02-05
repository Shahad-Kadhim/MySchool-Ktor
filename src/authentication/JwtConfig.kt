package com.example.authentication

import com.auth0.jwt.*
import com.auth0.jwt.algorithms.Algorithm
import com.example.secret
import com.example.util.toRole
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import java.util.*

class JwtConfig(jwtSecret: String) {

    companion object {
        // jwt config
         const val jwtIssuer = "com.example"
         const val jwtRealm = "com.example.mySchool"
         const val audience = "mySchool"


        // claims
         const val CLAIM_ID = "id"
         const val CLAIM_ROLE = "role"
    }

     val jwtAlgorithm = Algorithm.HMAC512(jwtSecret)
     val jwtVerifier: JWTVerifier =JWT
         .require(jwtAlgorithm)
         .withAudience(audience)
         .withIssuer(jwtIssuer)
         .build()


    fun generateToken(user: JwtUser): String =
        JWT.create()
            .withAudience(audience)
            .withIssuer(jwtIssuer)
            .withClaim(CLAIM_ID, user.id)
            .withClaim(CLAIM_ROLE, user.role.toString())
            .withExpiresAt(Date(System.currentTimeMillis()+64000000))
            .sign(jwtAlgorithm)

    fun teacherAuth(auth:Authentication.Configuration){
        auth.jwt("auth-teacher") {
            realm = jwtRealm
            verifier(jwtVerifier)
            validate{
                isSameRole(Role.TEACHER,it)
            }
        }
    }

    fun studentAuth(auth:Authentication.Configuration){
        auth.jwt("auth-student") {
            realm = jwtRealm
            verifier(jwtVerifier)
            validate{
                isSameRole(Role.STUDENT,it)
            }
        }
    }

    fun mangerAuth(auth:Authentication.Configuration){
        auth.jwt("auth-manger") {
            realm = jwtRealm
            verifier(jwtVerifier)
            validate{
                isSameRole(Role.MANGER,it)
            }
        }
    }

    private fun isSameRole(role: Role, credential: JWTCredential): JWTPrincipal?{
        return  takeIf {
            credential.payload.getClaim(CLAIM_ID).asString() != null && credential.payload.getClaim(CLAIM_ROLE).asString().toRole() == role
        }?.let{
            JWTPrincipal(credential.payload)
        }
    }

    data class JwtUser( val id: String,val role: Role): Principal

}
