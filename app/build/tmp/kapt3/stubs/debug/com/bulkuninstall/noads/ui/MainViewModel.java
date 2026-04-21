package com.bulkuninstall.noads.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0007\u0018\u00002\u00020\u0001B!\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0001\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\b\u0010\u0010\u001a\u00020\u0011H\u0002J\u0006\u0010\u0012\u001a\u00020\u0011J\u0006\u0010\u0013\u001a\u00020\u0011J\u0006\u0010\u0014\u001a\u00020\u0011J\b\u0010\u0015\u001a\u00020\u0011H\u0002J\u000e\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u0018J\u000e\u0010\u0019\u001a\u00020\u00112\u0006\u0010\u001a\u001a\u00020\u001bJ\u0006\u0010\u001c\u001a\u00020\u0011J\u000e\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u0018R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\r\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f\u00a8\u0006\u001f"}, d2 = {"Lcom/bulkuninstall/noads/ui/MainViewModel;", "Landroidx/lifecycle/ViewModel;", "getInstalledAppsUseCase", "Lcom/bulkuninstall/noads/domain/usecase/GetInstalledAppsUseCase;", "preferencesManager", "Lcom/bulkuninstall/noads/data/datastore/PreferencesManager;", "context", "Landroid/content/Context;", "(Lcom/bulkuninstall/noads/domain/usecase/GetInstalledAppsUseCase;Lcom/bulkuninstall/noads/data/datastore/PreferencesManager;Landroid/content/Context;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/bulkuninstall/noads/ui/MainUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "applyFilters", "", "checkAccessibilityService", "checkUsageAccess", "clearSelection", "observeSettings", "onSearchQueryChange", "query", "", "onSortChange", "sort", "Lcom/bulkuninstall/noads/data/model/SortType;", "refreshApps", "toggleSelection", "packageName", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class MainViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.bulkuninstall.noads.domain.usecase.GetInstalledAppsUseCase getInstalledAppsUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bulkuninstall.noads.data.datastore.PreferencesManager preferencesManager = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.bulkuninstall.noads.ui.MainUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.bulkuninstall.noads.ui.MainUiState> uiState = null;
    
    @javax.inject.Inject()
    public MainViewModel(@org.jetbrains.annotations.NotNull()
    com.bulkuninstall.noads.domain.usecase.GetInstalledAppsUseCase getInstalledAppsUseCase, @org.jetbrains.annotations.NotNull()
    com.bulkuninstall.noads.data.datastore.PreferencesManager preferencesManager, @dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.bulkuninstall.noads.ui.MainUiState> getUiState() {
        return null;
    }
    
    public final void checkUsageAccess() {
    }
    
    public final void checkAccessibilityService() {
    }
    
    private final void observeSettings() {
    }
    
    public final void refreshApps() {
    }
    
    public final void onSearchQueryChange(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void onSortChange(@org.jetbrains.annotations.NotNull()
    com.bulkuninstall.noads.data.model.SortType sort) {
    }
    
    public final void toggleSelection(@org.jetbrains.annotations.NotNull()
    java.lang.String packageName) {
    }
    
    public final void clearSelection() {
    }
    
    private final void applyFilters() {
    }
}