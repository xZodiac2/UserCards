package com.ilya.data

import com.ilya.data.retrofit.DummyJsonApi
import com.ilya.data.retrofit.models.User
import javax.inject.Inject

class UsersRepository @Inject internal constructor(
    private val api: DummyJsonApi,
) {
    
    suspend fun getAllUsers(): List<User> {
        return api.getAllUser().users
    }
    
    suspend fun getUserById(id: Int): User {
        return api.getUserById(id)
    }
    
}