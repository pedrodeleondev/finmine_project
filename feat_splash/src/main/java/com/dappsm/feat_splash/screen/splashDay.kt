package com.dappsm.feat_splash.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.dappsm.feat_splash.R
import com.dappsm.nav_core.AppScreens
import kotlinx.coroutines.delay

@Composable
fun SplashDayScreen(navController: NavController) {
    LaunchedEffect(true) {
        delay(3000)
        navController.popBackStack()
        navController.navigate(AppScreens.Login.route)
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF3B3039)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logoday),
            contentDescription = "Logo Day",
            modifier = Modifier.size(240.dp).padding(top=15.dp)
        )
    }
}