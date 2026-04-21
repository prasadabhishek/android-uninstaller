package com.bulkuninstall.noads.ui

import android.app.AppOpsManager
import android.content.Context
import android.os.Process
import android.provider.Settings
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bulkuninstall.noads.AutoUninstallService
import com.bulkuninstall.noads.data.datastore.PreferencesManager
import com.bulkuninstall.noads.data.model.AppInfo
import com.bulkuninstall.noads.data.model.SortType
import com.bulkuninstall.noads.domain.usecase.GetInstalledAppsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainUiState(
    val apps: List<AppInfo> = emptyList(),
    val filteredApps: List<AppInfo> = emptyList(),
    val selectedPackages: Set<String> = emptySet(),
    val searchQuery: String = "",
    val activeSort: SortType = SortType.NAME,
    val isLoading: Boolean = false,
    val isAccessibilityServiceEnabled: Boolean = false,
    val isUsageAccessEnabled: Boolean = false
)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getInstalledAppsUseCase: GetInstalledAppsUseCase,
    private val preferencesManager: PreferencesManager,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        observeSettings()
        refreshApps()
        checkAccessibilityService()
        checkUsageAccess()
    }

    fun checkUsageAccess() {
        val appOps = context.getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
        val mode = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            appOps.unsafeCheckOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), context.packageName)
        } else {
            @Suppress("DEPRECATION")
            appOps.checkOpNoThrow(AppOpsManager.OPSTR_GET_USAGE_STATS, Process.myUid(), context.packageName)
        }
        val isNowEnabled = mode == AppOpsManager.MODE_ALLOWED
        val wasEnabled = _uiState.value.isUsageAccessEnabled

        _uiState.update { it.copy(isUsageAccessEnabled = isNowEnabled) }
        
        // If it was just enabled, force a refresh to catch new usage data
        if (isNowEnabled && !wasEnabled) {
            refreshApps()
        }
    }

    fun checkAccessibilityService() {
        val serviceName = context.packageName + "/" + AutoUninstallService::class.java.canonicalName
        val enabledServices = Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )
        val isEnabled = enabledServices?.contains(serviceName) == true
        _uiState.update { it.copy(isAccessibilityServiceEnabled = isEnabled) }
    }

    private fun observeSettings() {
        viewModelScope.launch {
            preferencesManager.defaultSort.collect { sort ->
                _uiState.update { it.copy(activeSort = sort) }
                applyFilters()
            }
        }
    }

    fun refreshApps() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val apps = getInstalledAppsUseCase()
            _uiState.update { it.copy(apps = apps, isLoading = false) }
            applyFilters()
        }
    }

    fun onSearchQueryChange(query: String) {
        _uiState.update { it.copy(searchQuery = query) }
        applyFilters()
    }

    fun onSortChange(sort: SortType) {
        viewModelScope.launch {
            preferencesManager.updateDefaultSort(sort)
        }
    }

    fun toggleSelection(packageName: String) {
        _uiState.update { state ->
            val selection = state.selectedPackages.toMutableSet()
            if (selection.contains(packageName)) {
                selection.remove(packageName)
            } else {
                selection.add(packageName)
            }
            state.copy(selectedPackages = selection)
        }
    }

    fun clearSelection() {
        _uiState.update { it.copy(selectedPackages = emptySet()) }
    }

    private fun applyFilters() {
        _uiState.update { state ->
            // 1. Always Filter out system apps
            var filtered = state.apps.filter { !it.isSystemApp }

            // 2. Filter by search query
            if (state.searchQuery.isNotEmpty()) {
                filtered = filtered.filter {
                    it.appName.contains(state.searchQuery, ignoreCase = true) ||
                    it.packageName.contains(state.searchQuery, ignoreCase = true)
                }
            }

            // 3. Sort
            filtered = when (state.activeSort) {
                SortType.NAME -> filtered.sortedBy { it.appName.lowercase() }
                SortType.SIZE_DESC -> filtered.sortedByDescending { it.sizeBytes }
                SortType.DATE_INSTALLED_NEW -> filtered.sortedByDescending { it.installTimeMillis }
                SortType.DATE_INSTALLED_OLD -> filtered.sortedBy { it.installTimeMillis }
                SortType.LAST_USED_NEW -> filtered.sortedByDescending { it.lastUsedTimeMillis ?: 0L }
                SortType.LAST_USED_OLD -> filtered.sortedBy { it.lastUsedTimeMillis ?: Long.MAX_VALUE }
            }

            state.copy(filteredApps = filtered)
        }
    }
}
