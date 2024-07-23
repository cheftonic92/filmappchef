package com.cheftonic.filmappchef3.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.cheftonic.filmappchef3.R

private val fuente = FontFamily(
    Font(R.font.kanit_black, FontWeight.Black),
    Font(R.font.kanit_bold, FontWeight.Bold),
    Font(R.font.kanit_extrabold, FontWeight.ExtraBold),
    Font(R.font.kanit_semibold, FontWeight.SemiBold),
    Font(R.font.kanit_regular, FontWeight.Normal),
    Font(R.font.kanit_medium, FontWeight.Medium),
    Font(R.font.kanit_light, FontWeight.Light),
    Font(R.font.kanit_extralight, FontWeight.ExtraLight)
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = fuente,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    displayLarge = TextStyle(
        fontFamily = fuente,
        fontWeight = FontWeight.Bold,
        fontSize = 25.sp,
        lineHeight = 32.sp,
        letterSpacing = 1.sp
    ),
    titleMedium = TextStyle(
        fontFamily = fuente,
        fontWeight = FontWeight.SemiBold,
        fontSize = 19.sp,
        lineHeight = 30.sp,
        letterSpacing = 0.75.sp
    ),
    labelMedium = TextStyle(
        fontFamily = fuente,
        fontWeight = FontWeight.Medium,
        fontSize = 15.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.2.sp
    )


)