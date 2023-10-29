package com.ilya.userinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilya.data.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
    
    fun getUserById(id: Int) {
        viewModelScope.launch {
            _stateFlow.value = UserInfoState.Loading
            try {
                _stateFlow.value = UserInfoState.ViewUserInfo(repository.getUserById(id))
            } catch (e: Exception) {
                _stateFlow.value = UserInfoState.ErrorHaveNotInternet
            }
        }
    }
    
    fun onHaveNotId() {
        _stateFlow.value = UserInfoState.ErrorHaveNotId
    }
    
    companion object {
        const val DEFAULT_USER_ID = -1
    }
    
}