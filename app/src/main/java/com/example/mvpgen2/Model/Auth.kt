package com.example.mvpgen2.Model

import java.util.*

data class Auth(
        var id: Int,
        var name: String,
        var username: String,
        var email: String,
        var password: String,
        var updatedAt: Date,
        var createdAt: Date,
        var token: String,
)