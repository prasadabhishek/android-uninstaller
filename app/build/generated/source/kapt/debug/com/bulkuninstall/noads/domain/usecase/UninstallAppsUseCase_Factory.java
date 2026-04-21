package com.bulkuninstall.noads.domain.usecase;

import com.bulkuninstall.noads.data.AppRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
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
public final class UninstallAppsUseCase_Factory implements Factory<UninstallAppsUseCase> {
  private final Provider<AppRepository> repositoryProvider;

  public UninstallAppsUseCase_Factory(Provider<AppRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public UninstallAppsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static UninstallAppsUseCase_Factory create(Provider<AppRepository> repositoryProvider) {
    return new UninstallAppsUseCase_Factory(repositoryProvider);
  }

  public static UninstallAppsUseCase newInstance(AppRepository repository) {
    return new UninstallAppsUseCase(repository);
  }
}
