package com.bulkuninstall.noads.ui;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0014\u001a\u00020\u0015H\u0007J\b\u0010\u0016\u001a\u00020\u0015H\u0007J\f\u0010\u0017\u001a\u00060\u0015j\u0002`\u0018H\u0007R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0019"}, d2 = {"Lcom/bulkuninstall/noads/ui/MainViewModelTest;", "", "()V", "appOpsManager", "Landroid/app/AppOpsManager;", "contentResolver", "Landroid/content/ContentResolver;", "context", "Landroid/content/Context;", "getInstalledAppsUseCase", "Lcom/bulkuninstall/noads/domain/usecase/GetInstalledAppsUseCase;", "preferencesManager", "Lcom/bulkuninstall/noads/data/datastore/PreferencesManager;", "testApps", "", "Lcom/bulkuninstall/noads/data/model/AppInfo;", "testDispatcher", "Lkotlinx/coroutines/test/TestDispatcher;", "viewModel", "Lcom/bulkuninstall/noads/ui/MainViewModel;", "setup", "", "tearDown", "viewModel loads apps and handles search", "Lkotlinx/coroutines/test/TestResult;", "app_debugUnitTest"})
@kotlin.OptIn(markerClass = {kotlinx.coroutines.ExperimentalCoroutinesApi.class})
public final class MainViewModelTest {
    @org.jetbrains.annotations.NotNull()
    private final com.bulkuninstall.noads.domain.usecase.GetInstalledAppsUseCase getInstalledAppsUseCase = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bulkuninstall.noads.data.datastore.PreferencesManager preferencesManager = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final android.app.AppOpsManager appOpsManager = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.ContentResolver contentResolver = null;
    private com.bulkuninstall.noads.ui.MainViewModel viewModel;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.test.TestDispatcher testDispatcher = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.bulkuninstall.noads.data.model.AppInfo> testApps = null;
    
    public MainViewModelTest() {
        super();
    }
    
    @org.junit.Before()
    public final void setup() {
    }
    
    @org.junit.After()
    public final void tearDown() {
    }
}