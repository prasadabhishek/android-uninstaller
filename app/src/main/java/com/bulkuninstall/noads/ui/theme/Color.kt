package com.bulkuninstall.noads.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Brush

val Background = Color(0xFF121212)
// Premium Dark Theme Palette
val BackgroundObsidian = Color(0xFF0A0A0B)
val SurfaceDark = Color(0xFF141417)
val SurfaceGlass = Color(0xBB1E1E24)

val CyberCyan = Color(0xFF00F2FF)
val NeonPurple = Color(0xFFBC13FE)
val RadiantPink = Color(0xFFFF00FF)
val WarningRed = Color(0xFFFF3B3B)

val GlowCyan = Color(0x6600F2FF)
val GlowPurple = Color(0x44BC13FE)

val TextPrimary = Color(0xFFF0F0F3)
val TextSecondary = Color(0xFFA0A0AB)
val TextTertiary = Color(0xFF71717A)

// Gradients
val PremiumGradient = Brush.verticalGradient(
    colors = listOf(CyberCyan, NeonPurple)
)
val CardGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF1E1E24), Color(0xFF141417))
)
