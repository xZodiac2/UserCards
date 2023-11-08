package com.ilya.data.retrofit.models

import com.squareup.moshi.Json

data class Users(
    @Json(name = "users") val users: List<User>,
)

data class User(
    @Json(name = "id") val id: Int,
    @Json(name = "firstName") val firstName: String,
    @Json(name = "lastName") val lastName: String,
    @Json(name = "image") val image: String,
    @Json(name = "hair") val hair: Hair,
)

data class Hair(
    @Json(name = "color") val color: String,
    @Json(name = "type") val type: String,
)
