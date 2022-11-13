package com.app.currency.android.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun Heading(){
    Column(modifier =Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Currency Converter",
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp,
            color = Color.White
        )
    }

}