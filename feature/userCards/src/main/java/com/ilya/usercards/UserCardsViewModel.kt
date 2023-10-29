package com.ilya.usercards

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ilya.data.UsersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
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
    
    fun getAllUsers() {
        viewModelScope.launch {
            _stateFlow.value = UserCardsState.Loading
            try {
                _stateFlow.value = UserCardsState.ViewUserCards(repository.getAllUsers())
            } catch (e: Exception) {
                _stateFlow.value = UserCardsState.ErrorHaveNotInternet
            }
        }
    }
    
}