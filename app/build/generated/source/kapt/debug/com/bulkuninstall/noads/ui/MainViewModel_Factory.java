package com.bulkuninstall.noads.ui;

import android.content.Context;
import com.bulkuninstall.noads.data.datastore.PreferencesManager;
import com.bulkuninstall.noads.domain.usecase.GetInstalledAppsUseCase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class MainViewModel_Factory implements Factory<MainViewModel> {
  private final Provider<GetInstalledAppsUseCase> getInstalledAppsUseCaseProvider;

  private final Provider<PreferencesManager> preferencesManagerProvider;

  private final Provider<Context> contextProvider;

  public MainViewModel_Factory(Provider<GetInstalledAppsUseCase> getInstalledAppsUseCaseProvider,
      Provider<PreferencesManager> preferencesManagerProvider, Provider<Context> contextProvider) {
    this.getInstalledAppsUseCaseProvider = getInstalledAppsUseCaseProvider;
    this.preferencesManagerProvider = preferencesManagerProvider;
    this.contextProvider = contextProvider;
  }

  @Override
  public MainViewModel get() {
    return newInstance(getInstalledAppsUseCaseProvider.get(), preferencesManagerProvider.get(), contextProvider.get());
  }

  public static MainViewModel_Factory create(
      Provider<GetInstalledAppsUseCase> getInstalledAppsUseCaseProvider,
      Provider<PreferencesManager> preferencesManagerProvider, Provider<Context> contextProvider) {
    return new MainViewModel_Factory(getInstalledAppsUseCaseProvider, preferencesManagerProvider, contextProvider);
  }

  public static MainViewModel newInstance(GetInstalledAppsUseCase getInstalledAppsUseCase,
      PreferencesManager preferencesManager, Context context) {
    return new MainViewModel(getInstalledAppsUseCase, preferencesManager, context);
  }
}
