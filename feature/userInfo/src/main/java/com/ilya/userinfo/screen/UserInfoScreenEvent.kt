package com.ilya.userinfo.screen


sealed interface UserInfoScreenEvent {
    object HaveNotId : UserInfoScreenEvent
    data class Start(val userId: Int) : UserInfoScreenEvent
}
