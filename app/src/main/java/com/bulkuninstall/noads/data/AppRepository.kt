package com.bulkuninstall.noads.data

import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.bulkuninstall.noads.data.model.AppInfo
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {
    private val packageManager: PackageManager = context.packageManager
    private val usageStatsManager = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager

    suspend fun getInstalledApps(): List<AppInfo> = withContext(Dispatchers.IO) {
        val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
        val lastUsedStats = getLastUsedStats()

        apps.mapNotNull { appInfo ->
            try {
                val packageInfo = packageManager.getPackageInfo(appInfo.packageName, 0)
                val isSystemApp = (appInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0
                val apkFile = File(appInfo.sourceDir)
                val size = apkFile.length()

                AppInfo(
                    packageName = appInfo.packageName,
                    appName = packageManager.getApplicationLabel(appInfo).toString(),
                    icon = packageManager.getApplicationIcon(appInfo),
                    sizeBytes = size,
                    installTimeMillis = packageInfo.firstInstallTime,
                    updateTimeMillis = packageInfo.lastUpdateTime,
                    lastUsedTimeMillis = lastUsedStats[appInfo.packageName],
                    isSystemApp = isSystemApp,
                    versionName = packageInfo.versionName ?: "0",
                    versionCode = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.P) {
                        packageInfo.longVersionCode
                    } else {
                        packageInfo.versionCode.toLong()
                    }
                )
            } catch (e: Exception) {
                null
            }
        }
    }

    private fun getLastUsedStats(): Map<String, Long> {
        val currentTime = System.currentTimeMillis()
        val stats = usageStatsManager.queryAndAggregateUsageStats(currentTime - 1000L * 60 * 60 * 24 * 30, currentTime)
        return stats.mapValues { it.value.lastTimeUsed }
    }
}
