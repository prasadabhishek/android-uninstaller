package com.bulkuninstall.noads.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bulkuninstall.noads.ui.theme.RadiantPink
import com.bulkuninstall.noads.ui.theme.WarningRed

@Composable
fun SelectionFab(
    count: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AnimatedVisibility(
        visible = count > 0,
        enter = scaleIn(),
        exit = scaleOut(),
        modifier = modifier
    ) {
        ExtendedFloatingActionButton(
            onClick = onClick,
            containerColor = WarningRed,
            contentColor = Color.White,
            shape = RoundedCornerShape(24.dp),
            elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation(
                defaultElevation = 8.dp,
                pressedElevation = 12.dp
            ),
            icon = { 
                Icon(
                    Icons.Default.DeleteSweep, 
                    contentDescription = null,
                    tint = Color.White
                ) 
            },
            text = { 
                Text(
                    text = "UNINSTALL ($count)",
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Black,
                        letterSpacing = 1.sp,
                        fontSize = 14.sp
                    )
                ) 
            },
            modifier = Modifier.padding(16.dp)
        )
    }
}
