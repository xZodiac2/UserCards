package com.ilya.usercards

import com.ilya.data.retrofit.models.User

sealed interface UserCardsState {
    object Loading : UserCardsState
    data class Error(val error: UserCardsError) : UserCardsState
    data class ViewUserCards(val users: List<User>) : UserCardsState
}
