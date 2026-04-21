package com.bulkuninstall.noads.data;

@javax.inject.Singleton()
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010\u000e\n\u0002\u0010\t\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0011\b\u0007\u0012\b\b\u0001\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0086@\u00a2\u0006\u0002\u0010\fJ\u0014\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00100\u000eH\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/bulkuninstall/noads/data/AppRepository;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "packageManager", "Landroid/content/pm/PackageManager;", "usageStatsManager", "Landroid/app/usage/UsageStatsManager;", "getInstalledApps", "", "Lcom/bulkuninstall/noads/data/model/AppInfo;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "getLastUsedStats", "", "", "", "app_debug"})
public final class AppRepository {
    @org.jetbrains.annotations.NotNull()
    private final android.content.Context context = null;
    @org.jetbrains.annotations.NotNull()
    private final android.content.pm.PackageManager packageManager = null;
    @org.jetbrains.annotations.NotNull()
    private final android.app.usage.UsageStatsManager usageStatsManager = null;
    
    @javax.inject.Inject()
    public AppRepository(@dagger.hilt.android.qualifiers.ApplicationContext()
    @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object getInstalledApps(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.bulkuninstall.noads.data.model.AppInfo>> $completion) {
        return null;
    }
    
    private final java.util.Map<java.lang.String, java.lang.Long> getLastUsedStats() {
        return null;
    }
}