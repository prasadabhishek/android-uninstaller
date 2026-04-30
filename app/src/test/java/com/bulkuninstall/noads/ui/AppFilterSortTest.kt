package com.bulkuninstall.noads.ui

import com.bulkuninstall.noads.data.model.AppInfo
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Test

class AppFilterSortTest {

    @Test
    fun `filter out system apps`() {
        val apps = listOf(
            createAppInfo("pkg1", "User App", 1000L, false),
            createAppInfo("pkg2", "System App", 2000L, true)
        )

        val filtered = apps.filter { !it.isSystemApp }

        assertEquals(1, filtered.size)
        assertEquals("User App", filtered[0].appName)
    }

    @Test
    fun `search filters by app name case insensitive`() {
        val apps = listOf(
            createAppInfo("pkg1", "Calculator", 1000L, false),
            createAppInfo("pkg2", "camera", 2000L, false),
            createAppInfo("pkg3", "Browser", 3000L, false)
        )

        val searchQuery = "CAL"
        val filtered = apps.filter {
            it.appName.contains(searchQuery, ignoreCase = true)
        }

        assertEquals(1, filtered.size)
        assertEquals("Calculator", filtered[0].appName)
    }

    @Test
    fun `sort by name alphabetically`() {
        val apps = listOf(
            createAppInfo("pkg1", "Zebra", 1000L, false),
            createAppInfo("pkg2", "Alpha", 2000L, false)
        )

        val sorted = apps.sortedBy { it.appName.lowercase() }

        assertEquals("Alpha", sorted[0].appName)
        assertEquals("Zebra", sorted[1].appName)
    }

    @Test
    fun `sort by size descending`() {
        val apps = listOf(
            createAppInfo("pkg1", "Small", 1000L, false),
            createAppInfo("pkg2", "Big", 5000L, false)
        )

        val sorted = apps.sortedByDescending { it.sizeBytes }

        assertEquals("Big", sorted[0].appName)
        assertEquals("Small", sorted[1].appName)
    }

    @Test
    fun `sort by install date newest first`() {
        val apps = listOf(
            createAppInfo("pkg1", "Old", 1000L, false, installTime = 1000L),
            createAppInfo("pkg2", "New", 2000L, false, installTime = 2000L)
        )

        val sorted = apps.sortedByDescending { it.installTimeMillis }

        assertEquals("New", sorted[0].appName)
        assertEquals("Old", sorted[1].appName)
    }

    @Test
    fun `combined search and filter`() {
        val apps = listOf(
            createAppInfo("pkg1", "Calculator App", 1000L, false),
            createAppInfo("pkg2", "Calculator Pro", 2000L, true),
            createAppInfo("pkg3", "Browser", 3000L, false)
        )

        val searchQuery = "Calculator"
        val filtered = apps
            .filter { !it.isSystemApp }
            .filter { it.appName.contains(searchQuery, ignoreCase = true) }

        assertEquals(1, filtered.size)
        assertEquals("Calculator App", filtered[0].appName)
    }

    @Test
    fun `empty search returns all non-system apps`() {
        val apps = listOf(
            createAppInfo("pkg1", "App One", 1000L, false),
            createAppInfo("pkg2", "App Two", 2000L, false)
        )

        val filtered = apps.filter { !it.isSystemApp }

        assertEquals(2, filtered.size)
    }

    @Test
    fun `sort by last used newest`() {
        val apps = listOf(
            createAppInfo("pkg1", "Never Used", 1000L, false, lastUsed = null),
            createAppInfo("pkg2", "Recently Used", 2000L, false, lastUsed = 5000L),
            createAppInfo("pkg3", "Old Used", 3000L, false, lastUsed = 1000L)
        )

        val sorted = apps.sortedByDescending { it.lastUsedTimeMillis ?: 0L }

        assertEquals("Recently Used", sorted[0].appName)
        assertEquals("Old Used", sorted[1].appName)
        assertEquals("Never Used", sorted[2].appName)
    }

    private fun createAppInfo(
        packageName: String,
        appName: String,
        sizeBytes: Long,
        isSystemApp: Boolean,
        installTime: Long = 0L,
        lastUsed: Long? = null
    ): AppInfo {
        return AppInfo(
            packageName = packageName,
            appName = appName,
            icon = mockk(relaxed = true),
            sizeBytes = sizeBytes,
            installTimeMillis = installTime,
            updateTimeMillis = 0L,
            lastUsedTimeMillis = lastUsed,
            isSystemApp = isSystemApp,
            versionName = "1.0",
            versionCode = 1L
        )
    }
}