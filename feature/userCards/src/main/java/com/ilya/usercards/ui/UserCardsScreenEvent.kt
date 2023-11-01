package com.ilya.usercards.ui

sealed interface UserCardsScreenEvent {
    object Start : UserCardsScreenEvent
}