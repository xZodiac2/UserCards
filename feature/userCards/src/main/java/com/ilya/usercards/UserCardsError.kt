package com.ilya.usercards

sealed interface UserCardsError {
    object NoInternet : UserCardsError
}