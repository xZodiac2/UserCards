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
    
    fun handleEvent(event: UserInfoScreenEvent) {
        when (event) {
            is UserInfoScreenEvent.Start -> getUserById(event.userId)
            is UserInfoScreenEvent.HaveNotId -> onHaveNotId()
        }
    }
    
    private fun getUserById(id: Int) {
        _stateFlow.value = UserInfoState.Loading
        
        val exceptionHandler = CoroutineExceptionHandler { _, _ ->
            _stateFlow.value = UserInfoState.Error(UserInfoError.ErrorHaveNotInternet)
        }
        
        viewModelScope.launch(exceptionHandler) {
            val user = repository.getUserById(id)
            _stateFlow.value = UserInfoState.ViewUserInfo(user)
        }
    }
    
    private fun onHaveNotId() {
        _stateFlow.value = UserInfoState.Error(UserInfoError.ErrorHaveNotId)
    }
    
    companion object {
        const val DEFAULT_USER_ID = -1
    }
    
}