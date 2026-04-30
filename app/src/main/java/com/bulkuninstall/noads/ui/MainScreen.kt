package com.bulkuninstall.noads.ui

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import kotlinx.coroutines.launch
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.border
import androidx.compose.foundation.background
import com.bulkuninstall.noads.R
import com.bulkuninstall.noads.ui.components.*
import com.bulkuninstall.noads.ui.theme.*
import com.bulkuninstall.noads.ui.dialogs.AppDetailsDialog
import com.bulkuninstall.noads.ui.dialogs.ConfirmUninstallDialog
import com.bulkuninstall.noads.ui.dialogs.SuccessDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(viewModel: MainViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()
    val context = LocalContext.current
    val listState = rememberLazyListState()

    // Scroll to top when sort or search query changes
    LaunchedEffect(uiState.activeSort, uiState.searchQuery) {
        listState.scrollToItem(0)
    }

    DisposableEffect(Unit) {
        viewModel.checkAccessibilityService()
        viewModel.checkUsageAccess()
        onDispose {}
    }

    var showConfirmDialog by remember { mutableStateOf(false) }
    var showSuccessDialog by remember { mutableStateOf(false) }
    var appToDetail by remember { mutableStateOf<String?>(null) }
    
    var uninstalledCount by remember { mutableIntStateOf(0) }
    var freedSize by remember { mutableStateOf("") }

    // Uninstall flow logic
    val uninstallLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // After each uninstall, we refresh the list
        viewModel.refreshApps()
    }

    suspend fun performBatchUninstall(packages: Set<String>) {
        val apps = uiState.apps.filter { packages.contains(it.packageName) }
        uninstalledCount = apps.size
        freedSize = formatSize(apps.sumOf { it.sizeBytes })
        
        packages.forEach { pkg ->
            val intent = Intent(Intent.ACTION_DELETE).apply {
                data = Uri.parse("package:$pkg")
            }
            uninstallLauncher.launch(intent)
        }
        showSuccessDialog = true
        viewModel.clearSelection()
    }

    val coroutineScope = rememberCoroutineScope()

    var showSortMenu by remember { mutableStateOf(false) }
    var showUsageAccessSnackbar by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(showUsageAccessSnackbar) {
        if (showUsageAccessSnackbar) {
            kotlinx.coroutines.delay(4000)
            showUsageAccessSnackbar = false
        }
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = BackgroundObsidian,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            MediumTopAppBar(
                title = { 
                    Text(
                        stringResource(R.string.app_name),
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = (-1).sp
                        )
                    ) 
                },
                actions = {
                    var showMenu by remember { mutableStateOf(false) }
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu", tint = TextSecondary)
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false },
                        modifier = Modifier.background(SurfaceDark)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Show Permission Cards", color = TextPrimary) },
                            onClick = {
                                viewModel.showPermissionCards()
                                showMenu = false
                            },
                            leadingIcon = {
                                Icon(Icons.Default.Security, contentDescription = null, tint = CyberCyan)
                            }
                        )
                    }
                    IconButton(onClick = { showSortMenu = true }) {
                        Icon(Icons.Default.FilterList, contentDescription = "Sort", tint = CyberCyan)
                    }
                    DropdownMenu(
                        expanded = showSortMenu,
                        onDismissRequest = { showSortMenu = false },
                        modifier = Modifier.background(SurfaceDark)
                    ) {
                        var showUsageAccessSnackbar by remember { mutableStateOf(false) }

                        val availableSortOptions = com.bulkuninstall.noads.data.model.SortType.entries
                        availableSortOptions.forEach { sortType ->
                            val isLastUsedSort = sortType == com.bulkuninstall.noads.data.model.SortType.LAST_USED_NEW ||
                                                 sortType == com.bulkuninstall.noads.data.model.SortType.LAST_USED_OLD
                            val isDisabled = isLastUsedSort && !uiState.isUsageAccessEnabled

                            DropdownMenuItem(
                                text = { Text(sortType.displayName, color = if (isDisabled) TextSecondary else TextPrimary) },
                                onClick = {
                                    if (isDisabled) {
                                        showUsageAccessSnackbar = true
                                    } else {
                                        viewModel.onSortChange(sortType)
                                        showSortMenu = false
                                    }
                                },
                                leadingIcon = {
                                    if (isDisabled) {
                                        Icon(Icons.Default.Lock, contentDescription = null, tint = TextSecondary, modifier = Modifier.size(18.dp))
                                    } else null
                                },
                                trailingIcon = {
                                    if (uiState.activeSort == sortType && !isDisabled) {
                                        Icon(Icons.Default.Refresh, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(16.dp))
                                    }
                                }
                            )
                        }
                    }
                    IconButton(onClick = { viewModel.refreshApps() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh", tint = TextSecondary)
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = SurfaceDark.copy(alpha = 0.9f),
                    titleContentColor = TextPrimary
                ),
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            SelectionFab(
                count = uiState.selectedPackages.size,
                onClick = { showConfirmDialog = true }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            SearchBar(
                query = uiState.searchQuery,
                onQueryChange = viewModel::onSearchQueryChange,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            if (!uiState.isAccessibilityServiceEnabled && !uiState.superpowerCardDismissed) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .border(1.dp, PremiumGradient, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = GlowCyan.copy(alpha = 0.05f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .background(CyberCyan.copy(alpha = 0.2f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Refresh, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(16.dp))
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Enable Superpower Permission",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = CyberCyan
                                )
                            }
                            IconButton(onClick = { viewModel.dismissSuperpowerCard() }) {
                                Icon(Icons.Default.Close, contentDescription = "Dismiss", tint = TextSecondary, modifier = Modifier.size(20.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Automate uninstall confirmations by enabling our Accessibility Service.",
                            style = MaterialTheme.typography.labelMedium.copy(color = TextSecondary)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                                context.startActivity(intent)
                            },
                            modifier = Modifier.height(40.dp).fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = CyberCyan),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("Enable Superpower", color = Color.Black, style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                        }
                    }
                }
            }

            if (!uiState.isUsageAccessEnabled && !uiState.usageAccessCardDismissed) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                        .border(1.dp, PremiumGradient, RoundedCornerShape(16.dp)),
                    colors = CardDefaults.cardColors(
                        containerColor = GlowCyan.copy(alpha = 0.05f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(28.dp)
                                        .background(CyberCyan.copy(alpha = 0.2f), CircleShape),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.History, contentDescription = null, tint = CyberCyan, modifier = Modifier.size(16.dp))
                                }
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Enable Usage Access",
                                    style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                                    color = CyberCyan
                                )
                            }
                            IconButton(onClick = { viewModel.dismissUsageAccessCard() }) {
                                Icon(Icons.Default.Close, contentDescription = "Dismiss", tint = TextSecondary, modifier = Modifier.size(20.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Grant Usage Access to sort apps by how recently they were used.",
                            style = MaterialTheme.typography.labelMedium.copy(color = TextSecondary)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = {
                                val intent = Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS).apply {
                                    data = Uri.parse("package:" + context.packageName)
                                }
                                try {
                                    context.startActivity(intent)
                                } catch (e: Exception) {
                                    context.startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
                                }
                            },
                            modifier = Modifier.height(40.dp).fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = CyberCyan),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text("Grant Access", color = Color.Black, style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold))
                        }
                    }
                }
            }

            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (uiState.filteredApps.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = if (uiState.searchQuery.isNotEmpty()) "No apps match your search" else "No apps to uninstall",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState
                ) {
                    items(uiState.filteredApps, key = { it.packageName }) { app ->
                        AppListItem(
                            app = app,
                            selected = uiState.selectedPackages.contains(app.packageName),
                            onSelectedChange = { viewModel.toggleSelection(app.packageName) },
                            onClick = {
                                if (app.isSystemApp) {
                                    appToDetail = app.appName
                                } else {
                                    viewModel.toggleSelection(app.packageName)
                                }
                            },
                            onLongClick = {
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.parse("package:${app.packageName}")
                                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                }
                                context.startActivity(intent)
                            }
                        )
                    }
                }
            }
        }
    }

    if (showConfirmDialog) {
        val selectedApps = uiState.apps.filter { uiState.selectedPackages.contains(it.packageName) }
        ConfirmUninstallDialog(
            selectedAppsNames = selectedApps.map { it.appName },
            totalSize = selectedApps.sumOf { it.sizeBytes },
            onConfirm = {
                showConfirmDialog = false
                // Note: performBatchUninstall is suspend but we are calling it in a way that doesn't strictly wait here
                // since intents are system-side.
                coroutineScope.launch {
                    performBatchUninstall(uiState.selectedPackages)
                }
            },
            onDismiss = { 
                showConfirmDialog = false
                viewModel.clearSelection()
            }
        )
    }

    if (showSuccessDialog) {
        SuccessDialog(
            count = uninstalledCount,
            freedSize = freedSize,
            onDismiss = { showSuccessDialog = false }
        )
    }

    appToDetail?.let { name ->
        AppDetailsDialog(
            appName = name,
            onDismiss = { appToDetail = null }
        )
    }
}

private fun formatSize(size: Long): String {
    val kb = size / 1024
    if (kb < 1024) return "${kb}KB"
    val mb = kb / 1024
    if (mb < 1024) return "${mb}MB"
    val gb = mb / 1024.0
    return String.format("%.1fGB", gb)
}
