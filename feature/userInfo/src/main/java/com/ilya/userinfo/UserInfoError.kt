package com.ilya.userinfo

sealed interface UserInfoError {
    object ErrorHaveNotInternet : UserInfoError
    object ErrorHaveNotId : UserInfoError
}