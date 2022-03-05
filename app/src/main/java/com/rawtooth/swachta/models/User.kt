package com.rawtooth.swachta.models

data class User(
    val accountNonExpired: Boolean,
    val accountNonLocked: Boolean,
    val authorities: List<Authority>,
    val credentialsNonExpired: Boolean,
    val email: String,
    val enabled: Boolean,
    val password: String,
    val phonenumber: String,
    val profile: String,
    val userId: Int,
    val username: String
)