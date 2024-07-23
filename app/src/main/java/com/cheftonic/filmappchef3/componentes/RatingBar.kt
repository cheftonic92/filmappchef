package com.cheftonic.filmappchef3.componentes

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RatingBar(
    maxVal: Int = 10,
    iniVal: Int = 0,
    onValChange: (Int) -> Unit
){
    var puntuacion by remember {
        mutableStateOf(iniVal)
    }

    LaunchedEffect(iniVal) {
        puntuacion = iniVal
    }

    Row (
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(maxVal){
            index -> Icon(
                imageVector =
                    Icons.Filled.Star,
                contentDescription = null,
                tint =
                if (index < puntuacion)
                    Color.Yellow
                else
                    Color.LightGray,
                modifier = Modifier
                    .size(34.dp)
                    .clickable {
                        puntuacion = index + 1
                        onValChange(puntuacion)
                    }
            )
        }
    }
}