package com.ilya.data

import com.ilya.data.retrofit.DummyJsonApi
import com.ilya.data.retrofit.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UsersRepository @Inject internal constructor(
    private val api: DummyJsonApi,
) {
    
    suspend fun getAllUsers(): List<User> {
        return withContext(Dispatchers.IO) { api.getAllUsers().users }
    }
    
    suspend fun getUserById(id: Int): User {
        return withContext(Dispatchers.IO) { api.getUserById(id) }
    }
    
}