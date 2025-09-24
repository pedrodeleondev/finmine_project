package com.dappsm.feat_auth.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.dappsm.feat_auth.viewmodel.authviewmodel

@Composable
fun AppNavigationLS(authviewmodel: authviewmodel){
    val navController=rememberNavController()
    NavHost(navController=navController, startDestination = "login",builder ={
        composable ("login"){
            loginScreen(navController)
        }
    })
}