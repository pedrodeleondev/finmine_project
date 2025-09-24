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
fun splashDayScreen(){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF3B3039)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logoday),
            contentDescription = "Logo Day",
            modifier = Modifier.size(250.dp)
        )
    }
}
@Preview
@Composable
fun ProbandoPantalla(){
    splashDayScreen()
}