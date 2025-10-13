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
    color2 = Color(0xFF181818),   // Cinza quase preto
    color3 = Color(0xFF303030),   // Cinza bem escuro
    color4 = Color(0xFF484848),   // Cinza escuro
    color5 = Color(0xFF606060),   // Cinza médio-escuro
    color6 = Color(0xFF787878),   // Cinza médio
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
    // Tons de cinza invertidos (de escuro para claro)
    color1 = Color(0xFFFFFFFF),   // Branco
    color2 = Color(0xFFF5F5F5),   // Cinza muito claro
    color3 = Color(0xFFEBEBEB),   // Cinza claro
    color4 = Color(0xFFDCDCDC),   // Cinza claro médio
    color5 = Color(0xFFC8C8C8),   // Cinza médio claro
    color6 = Color(0xFFBEBEBE),   // Cinza médio

    // Cores de destaque mantidas
    color7 = Color(0xFF94E60E),   // Verde vibrante
    color8 = Color(0xFF00B84A),   // Verde escuro
    color9 = Color(0xFF69E09A),   // Verde claro
    color10 = Color(0xFFFFD24D),  // Amarelo suave

    // Cores "on" invertidas para garantir contraste no fundo claro
    onColor1 = Color(0xFF1A1A1A), // Preto suave
    onColor2 = Color(0xFF1A1A1A), // Preto suave
    onColor3 = Color(0xFF1A1A1A), // Preto suave
    onColor4 = Color(0xFF1A1A1A), // Preto suave
    onColor5 = Color(0xFF1A1A1A), // Preto suave
    onColor6 = Color(0xFF1A1A1A), // Preto suave

    // Cores "on" para os destaques, garantindo legibilidade
    onColor7 = Color(0xFF1A1A1A), // Preto suave
    onColor8 = Color(0xFFFFFFFF), // Branco (para contraste com o verde escuro)
    onColor9 = Color(0xFF1A1A1A), // Preto suave
    onColor10 = Color(0xFF1A1A1A) // Preto suave
)
