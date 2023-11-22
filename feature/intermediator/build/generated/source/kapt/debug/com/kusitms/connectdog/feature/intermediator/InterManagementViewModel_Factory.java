package com.kusitms.connectdog.feature.intermediator;

import com.kusitms.connectdog.core.data.repository.InterManagementRepository;
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
public final class InterManagementViewModel_Factory implements Factory<InterManagementViewModel> {
  private final Provider<InterManagementRepository> managementRepositoryProvider;

  public InterManagementViewModel_Factory(
      Provider<InterManagementRepository> managementRepositoryProvider) {
    this.managementRepositoryProvider = managementRepositoryProvider;
  }

  @Override
  public InterManagementViewModel get() {
    return newInstance(managementRepositoryProvider.get());
  }

  public static InterManagementViewModel_Factory create(
      Provider<InterManagementRepository> managementRepositoryProvider) {
    return new InterManagementViewModel_Factory(managementRepositoryProvider);
  }

  public static InterManagementViewModel newInstance(
      InterManagementRepository managementRepository) {
    return new InterManagementViewModel(managementRepository);
  }
}
