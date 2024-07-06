package com.mdlicht.zb.composesampleapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mdlicht.zb.composesampleapp.example.list.ListExampleScreen

@Composable
fun SampleAppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "main") {
        composable("main") {
            MainScreen(navController = navController)
        }
        composable("listExampleScreen") {
            ListExampleScreen()
        }
    }
}