package com.ilya.usercards

sealed interface UserCardsError {
    object ErrorHaveNotInternet : UserCardsError
}