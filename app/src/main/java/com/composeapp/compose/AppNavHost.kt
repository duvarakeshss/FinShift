package com.composeapp.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            Login(
                onNavigateToSignup = { navController.navigate("signup") },
                onLoginSuccess = {
                    navController.navigate("welcome") {
                        popUpTo("login") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable("signup") {
            Signup(
                onNavigateBack = { navController.popBackStack() },
                onSignupSuccess = {
                    navController.navigate("login") {
                        popUpTo("login") { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
        composable("welcome") {
            WelcomeScreen()
        }
    }
}
