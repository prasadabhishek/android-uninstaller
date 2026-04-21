package com.bulkuninstall.noads.data.model

import android.graphics.drawable.Drawable

data class AppInfo(
    val packageName: String,
    val appName: String,
    val icon: Drawable,
    val sizeBytes: Long,
    val installTimeMillis: Long,
    val updateTimeMillis: Long,
    val lastUsedTimeMillis: Long?,
    val isSystemApp: Boolean,
    val versionName: String,
    val versionCode: Long
)

enum class SortType(val displayName: String) {
    NAME("Name (A-Z)"),
    SIZE_DESC("Size (largest first)"),
    DATE_INSTALLED_NEW("Date installed (newest)"),
    DATE_INSTALLED_OLD("Date installed (oldest)"),
    LAST_USED_NEW("Most used"),
    LAST_USED_OLD("Least used")
}
