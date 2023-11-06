package com.ilya.usercards

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ilya.usercards.navigation.Destination
import androidx.navigation.compose.NavHost
import com.ilya.usercards.ui.UserCardsScreen
import com.ilya.userinfo.screen.UserInfoScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            
            NavHost(navController = navController, startDestination = Destination.UserCards.route) {
                composable(Destination.UserCards.route) {
                    UserCardsScreen(onCardClick = { userId ->
                        navController.navigate(Destination.UserInfo.withArguments(userId.toString()))
                    })
                }
                composable(
                    route = Destination.UserInfo.withArgumentNames(ARG_NAME_USER_ID),
                    arguments = listOf(navArgument(ARG_NAME_USER_ID) { type = NavType.IntType })
                ) { backStackEntry ->
                    UserInfoScreen(
                        userId = backStackEntry.arguments?.getInt(ARG_NAME_USER_ID, DEFAULT_USER_ID) ?: DEFAULT_USER_ID,
                        onBackClick = {
                            navController.navigate(Destination.UserCards.route) {
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
        private const val ARG_NAME_USER_ID = "userId"
        private const val DEFAULT_USER_ID = -1
    }
    
}
