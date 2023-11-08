package com.ilya.userinfo

sealed interface UserInfoError {
    object NoInternet : UserInfoError
    object NoId : UserInfoError
}