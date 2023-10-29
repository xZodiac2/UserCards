package com.ilya.usercards

import com.ilya.data.retrofit.models.User

sealed class UserCardsState {
    object Loading : UserCardsState()
    object ErrorHaveNotInternet : UserCardsState()
    data class ViewUserCards(val users: List<User>) : UserCardsState()
}
