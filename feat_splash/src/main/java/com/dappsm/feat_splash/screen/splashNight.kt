package com.dappsm.feat_splash.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dappsm.feat_splash.R

@Composable
fun SplashNightScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF302D2D)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.logodark),
            contentDescription = "Logo Night",
            modifier = Modifier.size(222.dp)
        )
    }
}

@Preview
@Composable
fun PantallaSns(){
    SplashNightScreen()
}