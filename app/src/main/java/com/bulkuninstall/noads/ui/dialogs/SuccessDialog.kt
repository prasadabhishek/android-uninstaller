package com.bulkuninstall.noads.ui.dialogs

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.bulkuninstall.noads.R

@Composable
fun SuccessDialog(
    count: Int,
    freedSize: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.uninstall_success_title)) },
        text = {
            Text(
                stringResource(R.string.apps_uninstalled, count) + "\n" +
                stringResource(R.string.freed_size, freedSize)
            )
        },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text(stringResource(R.string.done))
            }
        }
    )
}
