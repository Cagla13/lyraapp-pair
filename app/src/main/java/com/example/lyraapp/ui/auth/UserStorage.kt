package com.example.lyraapp.ui.auth

object UserStorage {
    data class UserData(
        val firstName: String = "",
        val lastName: String = "",
        val email: String = "",
        val phoneNumber: String = "",
        val password: String = ""
    )

    var registeredUser: UserData? = null
    var isLoggedIn: Boolean = false

    fun isPasswordValid(password: String): Boolean {
        if (password.length < 8) return false
        return password.any { it.isDigit() }
    }
}