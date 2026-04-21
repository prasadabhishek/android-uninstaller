package com.bulkuninstall.noads.ui.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bulkuninstall.noads.R
import com.bulkuninstall.noads.ui.components.formatSize

@Composable
fun ConfirmUninstallDialog(
    selectedAppsNames: List<String>,
    totalSize: Long,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(stringResource(R.string.uninstall_confirm_title, selectedAppsNames.size))
        },
        text = {
            Column(modifier = Modifier.heightIn(max = 300.dp)) {
                LazyColumn(modifier = Modifier.weight(1f, fill = false)) {
                    items(selectedAppsNames) { name ->
                        Text(
                            text = "• $name",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(vertical = 2.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Total size to be freed: ${formatSize(totalSize)}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
            ) {
                Text(stringResource(R.string.uninstall))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}
