package com.ilya.userinfo

import com.ilya.data.retrofit.models.User

sealed interface UserInfoState {
    object Loading : UserInfoState
    data class Error(val error: UserInfoError) : UserInfoState
    data class ViewUserInfo(val user: User) : UserInfoState
}
