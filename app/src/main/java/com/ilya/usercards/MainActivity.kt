package com.ilya.usercards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ilya.usercards.navigation.Destination
import com.ilya.usercards.navigation.NavHost
import com.ilya.usercards.navigation.composable
import com.ilya.usercards.navigation.navigate
import com.ilya.userinfo.UserInfoScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            
            NavHost(navController = navController, startDestination = Destination.UserCards) {
                composable(Destination.UserCards) {
                    UserCardsScreen(onCardClick = { userId ->
                        navController.navigate(Destination.UserInfo.withArguments(userId))
                    })
                }
                composable(
                    Destination.UserInfo.withArgumentNames(KEY_USER_ID),
                    arguments = listOf(navArgument(KEY_USER_ID) { type = NavType.IntType })
                ) { backStackEntry ->
                    UserInfoScreen(
                        userId = backStackEntry.arguments?.getInt(KEY_USER_ID, DEFAULT_USER_ID),
                        onGoBackClick = {
                            navController.navigate(Destination.UserCards) {
                                popUpTo(Destination.UserCards.route)
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    }
    
    companion object {
        const val KEY_USER_ID = "userId"
        const val DEFAULT_USER_ID = -1
    }
    
}
