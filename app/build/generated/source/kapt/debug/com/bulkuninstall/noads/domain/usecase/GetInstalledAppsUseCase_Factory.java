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
public final class GetInstalledAppsUseCase_Factory implements Factory<GetInstalledAppsUseCase> {
  private final Provider<AppRepository> repositoryProvider;

  public GetInstalledAppsUseCase_Factory(Provider<AppRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GetInstalledAppsUseCase get() {
    return newInstance(repositoryProvider.get());
  }

  public static GetInstalledAppsUseCase_Factory create(Provider<AppRepository> repositoryProvider) {
    return new GetInstalledAppsUseCase_Factory(repositoryProvider);
  }

  public static GetInstalledAppsUseCase newInstance(AppRepository repository) {
    return new GetInstalledAppsUseCase(repository);
  }
}
