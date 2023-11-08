package com.ilya.usercards.navigation

sealed class Destination(val route: String) {
    
    object UserCards : Destination("userCards")
    object UserInfo : Destination("userInfo")
    
    fun withArguments(vararg arguments: String): String {
        return route + arguments.joinToString(prefix = "/", separator = "/")
    }
    
    fun withArgumentNames(vararg argumentNames: String): String {
        return route + argumentNames.joinToString(prefix = "/{", separator = "}/{", postfix = "}")
    }
    
}
