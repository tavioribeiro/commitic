package org.tavioribeiro.commitic.theme

import androidx.compose.ui.graphics.Color

data class AppColors(
    val color1: Color,
    val color2: Color,
    val color3: Color,
    val color4: Color,
    val color5: Color,
    val color6: Color,
    val color7: Color,
    val color8: Color,
    val color9: Color,
    val color10: Color,

    val onColor1: Color,
    val onColor2: Color,
    val onColor3: Color,
    val onColor4: Color,
    val onColor5: Color,
    val onColor6: Color,
    val onColor7: Color,
    val onColor8: Color,
    val onColor9: Color,
    val onColor10: Color,
)

val darkColors = AppColors(
    color1 = Color(0xFF000000),   // Preto
    color2 = Color(0xFF161616),   // Cinza quase preto
    color3 = Color(0xFF202020),   // Cinza bem escuro
    color4 = Color(0xFF2A2A2A),   // Cinza escuro
    color5 = Color(0xFF3B3B3B),   // Cinza médio-escuro
    color6 = Color(0xFF5A5A5A),   // Cinza médio
    color7 = Color(0xFF94E60E),   // Verde vibrante
    color8 = Color(0xFF00B84A),   // Verde escuro
    color9 = Color(0xFF69E09A),   // Verde claro
    color10 = Color(0xFFFFD24D),  // Amarelo suave

    onColor1 = Color(0xFFE4E4E4), // Cinza quase branco
    onColor2 = Color(0xFFDCDCDC), // Cinza claro
    onColor3 = Color(0xFFD2D2D2), // Cinza claro suave
    onColor4 = Color(0xFFC8C8C8), // Cinza claro mais quente
    onColor5 = Color(0xFFBEBEBE), // Cinza médio-claro
    onColor6 = Color(0xFFE4E4E4), // Cinza quase branco
    onColor7 = Color(0xFF1A1A1A), // Preto suave
    onColor8 = Color(0xFFEAEAEA), // Branco suave
    onColor9 = Color(0xFF1A1A1A), // Preto suave
    onColor10 = Color(0xFF1A1A1A) // Preto suave
)

val lightColors = AppColors(
    color1 = Color(0xFFFFFFFF),   // Branco
    color2 = Color(0xFFF7F7F7),   // Cinza muito claro
    color3 = Color(0xFFECECEC),   // Cinza claro
    color4 = Color(0xFFE2E2E2),   // Cinza médio-claro
    color5 = Color(0xFFD4D4D4),   // Cinza médio suave
    color6 = Color(0xFF9C9C9C),   // Cinza médio-escuro suave
    color7 = Color(0xFF86D10A),   // Verde escuro
    color8 = Color(0xFF1D6E07),   // Verde muito escuro
    color9 = Color(0xFF66BB6A),   // Verde médio
    color10 = Color(0xFFFFB84D),  // Laranja/Amarelo suave

    onColor1 = Color(0xFF1A1A1A), // Preto suave
    onColor2 = Color(0xFF1A1A1A), // Preto suave
    onColor3 = Color(0xFF2A2A2A), // Cinza bem escuro
    onColor4 = Color(0xFF2A2A2A), // Cinza bem escuro
    onColor5 = Color(0xFF2A2A2A), // Cinza bem escuro
    onColor6 = Color(0xFFEAEAEA), // Branco suave
    onColor7 = Color(0xFF1A1A1A), // Preto suave
    onColor8 = Color(0xFFEAEAEA), // Branco suave
    onColor9 = Color(0xFF1A1A1A), // Preto suave
    onColor10 = Color(0xFF1A1A1A) // Preto suave
)
