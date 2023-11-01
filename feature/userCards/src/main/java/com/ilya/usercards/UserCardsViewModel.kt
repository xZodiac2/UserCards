package com.ilya.usercards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilya.data.UsersRepository
import com.ilya.usercards.ui.UserCardsScreenEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserCardsViewModel @Inject constructor(
    private val repository: UsersRepository,
) : ViewModel() {
    
    private val _stateFlow = MutableStateFlow<UserCardsState>(UserCardsState.Loading)
    val stateFlow = _stateFlow.asStateFlow()
    
    fun handleEvent(event: UserCardsScreenEvent) {
        when (event) {
            is UserCardsScreenEvent.Start -> getAllUsers()
        }
    }
    
    private fun getAllUsers() {
        _stateFlow.value = UserCardsState.Loading
        
        val exceptionHandler = CoroutineExceptionHandler { _, _ ->
            _stateFlow.value = UserCardsState.Error(UserCardsError.ErrorHaveNotInternet)
        }
        
        viewModelScope.launch(exceptionHandler) {
            val users = repository.getAllUsers()
            _stateFlow.value = UserCardsState.ViewUserCards(users)
        }
    }
    
}