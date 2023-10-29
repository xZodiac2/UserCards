package com.ilya.data.retrofit.models

import com.squareup.moshi.Json


data class Users(
    @Json(name = "users") val users: List<User>,
)
