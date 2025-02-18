package james.apt3065.aicoachfitnessapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import james.apt3056.aicoachfitnessapp.R.*

// Set of Material typography styles to start with
val outfit = FontFamily(
    Font(font.robotoregular),
    Font(font.robotobold, FontWeight.Bold),
    Font(font.robotolight, FontWeight.Thin)
)

val nunito = FontFamily(
    Font(font.robotoregular),
    Font(font.robotobold, FontWeight.Bold),
    Font(font.robotothin, FontWeight.Thin)
)

val Typography = Typography(
    body1 = TextStyle(
        fontFamily = nunito,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    defaultFontFamily = outfit,

    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)