package com.jkangangi.en_dictionary.app.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jkangangi.en_dictionary.R


// custom font
val MerriWeatherFontFamily = FontFamily(
    Font(resId = R.font.merriweather_black, weight = FontWeight.Black),
    Font(resId = R.font.merriweather_black_italic, weight = FontWeight.Black),
    Font(resId = R.font.merriweather_bold, weight = FontWeight.Bold),
    Font(resId = R.font.merriweather_bold_italic, weight = FontWeight.SemiBold),
    Font(resId = R.font.merriweather_italic, weight = FontWeight.Thin),
    Font(resId = R.font.merriweather_light, weight = FontWeight.Light),
    Font(resId = R.font.merriweather_light_italic, weight = FontWeight.Medium),
    Font(resId = R.font.merriweather_regular, weight = FontWeight.Normal)
)

internal fun getTypography(fontFamily: FontFamily) = Typography(
    bodyLarge = TextStyle(
        fontFamily = fontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(fontSize = 16.sp, fontFamily = fontFamily),
    bodySmall = TextStyle(fontSize = 14.sp, fontFamily = fontFamily),
    headlineSmall = TextStyle(fontFamily = fontFamily),
    headlineMedium = TextStyle(fontFamily = fontFamily),
    headlineLarge = TextStyle(fontFamily = fontFamily),
)