package com.ilya.userinfo.screen


sealed interface UserInfoScreenEvent {
    data class Start(val userId: Int) : UserInfoScreenEvent
    data class Retry(val userId: Int) : UserInfoScreenEvent
}
