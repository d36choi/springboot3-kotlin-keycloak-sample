package io.keycloak.keycloaklogout.dto

import com.nimbusds.jose.shaded.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class KeycloakTokenPayload(
    @SerializedName("iat")
    val iat: Long,

    @SerializedName("jti")
    val jti: String,

    @SerializedName("iss")
    val iss: String,

    @SerializedName("aud")
    val aud: String,

    @SerializedName("sub")
    val sub: String,

    @SerializedName("typ")
    val typ: String,

    @SerializedName("sid")
    val sid: String,

)

