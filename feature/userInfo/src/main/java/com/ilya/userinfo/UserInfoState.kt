package com.ilya.userinfo

import com.ilya.data.retrofit.models.User

sealed class UserInfoState {
    object ErrorHaveNotId : UserInfoState()
    object ErrorHaveNotInternet : UserInfoState()
    object Loading : UserInfoState()
    data class ViewUserInfo(val user: User) : UserInfoState()
}
