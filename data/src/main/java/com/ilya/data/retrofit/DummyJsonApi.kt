package com.ilya.data.retrofit

import com.ilya.data.retrofit.models.User
import com.ilya.data.retrofit.models.Users
import retrofit2.http.GET
import retrofit2.http.Path


internal interface DummyJsonApi {
    
    @GET("/users")
    suspend fun getAllUsers(): Users
    
    @GET("/users/{id}")
    suspend fun getUserById(@Path("id") id: Int): User
    
    companion object {
        const val BASE_URL = "https://dummyjson.com/"
    }
    
}
