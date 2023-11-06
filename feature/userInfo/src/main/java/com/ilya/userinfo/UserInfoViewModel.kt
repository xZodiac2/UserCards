package com.ilya.userinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilya.data.UsersRepository
import com.ilya.userinfo.screen.UserInfoScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserInfoViewModel @Inject constructor(
    private val repository: UsersRepository,
) : ViewModel() {
    
    private val _stateFlow = MutableStateFlow<UserInfoState>(UserInfoState.Loading)
    val stateFlow = _stateFlow.asStateFlow()
    
    private val exceptionHandler = CoroutineExceptionHandler { _, _ ->
        _stateFlow.value = UserInfoState.Error(UserInfoError.NoInternet)
    }
    
    fun handleEvent(event: UserInfoScreenEvent) {
        when (event) {
            is UserInfoScreenEvent.Start -> onStart(event.userId)
            is UserInfoScreenEvent.Retry -> onRetry(event.userId)
        }
    }
    
    private fun onStart(userId: Int) {
        if (_stateFlow.value == UserInfoState.Loading) {
            getUserById(userId)
        }
    }
    
    private fun onRetry(userId: Int) {
        getUserById(userId)
    }
    
    private fun getUserById(id: Int) {
        if (id == DEFAULT_USER_ID) {
            _stateFlow.value = UserInfoState.Error(UserInfoError.NoId)
            return
        }
        
        _stateFlow.value = UserInfoState.Loading
        
        viewModelScope.launch(exceptionHandler) {
            val user = repository.getUserById(id)
            _stateFlow.value = UserInfoState.ViewUserInfo(user)
        }
    }
    
    companion object {
        const val DEFAULT_USER_ID = -1
    }
    
}