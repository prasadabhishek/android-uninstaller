package com.bulkuninstall.noads.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.bulkuninstall.noads.data.model.AppInfo
import com.bulkuninstall.noads.ui.theme.*
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppListItem(
    app: AppInfo,
    selected: Boolean,
    onSelectedChange: (Boolean) -> Unit,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 4.dp)
            .clip(RoundedCornerShape(14.dp))
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
            .then(
                if (selected) Modifier.border(1.dp, CyberCyan, RoundedCornerShape(14.dp))
                else Modifier.border(0.5.dp, Color.White.copy(alpha = 0.05f), RoundedCornerShape(14.dp))
            ),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) GlowCyan.copy(alpha = 0.08f) else Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Box(modifier = Modifier.background(CardGradient)) {
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Compact App Icon
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .background(
                            if (selected) CyberCyan.copy(alpha = 0.1f) else Color.White.copy(alpha = 0.05f),
                            RoundedCornerShape(10.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        bitmap = app.icon.toBitmap().asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(6.dp))
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = app.appName,
                        style = MaterialTheme.typography.titleSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = if (selected) CyberCyan else TextPrimary,
                            letterSpacing = 0.2.sp
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    
                    Spacer(modifier = Modifier.height(1.dp))
                    
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = formatSize(app.sizeBytes),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = TextSecondary,
                                fontWeight = FontWeight.Medium,
                                fontSize = 10.sp
                            )
                        )
                        Text(
                            text = " • ",
                            style = MaterialTheme.typography.labelSmall.copy(color = TextTertiary, fontSize = 10.sp),
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )
                        Text(
                            text = dateFormat.format(Date(app.installTimeMillis)),
                            style = MaterialTheme.typography.labelSmall.copy(color = TextTertiary, fontSize = 10.sp)
                        )
                    }
                }

                // Compact Selection Indicator
                IconButton(
                    onClick = { onSelectedChange(!selected) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = if (selected) Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                        contentDescription = "Selection State",
                        tint = if (selected) CyberCyan else TextTertiary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

fun formatSize(size: Long): String {
    val kb = size / 1024
    if (kb < 1024) return "${kb}KB"
    val mb = kb / 1024
    if (mb < 1024) return "${mb}MB"
    val gb = mb / 1024.0
    return String.format("%.1fGB", gb)
}
