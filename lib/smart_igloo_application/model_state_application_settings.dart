class ApplicationSettingsModelState {
  final bool? applicationIsInitialized;

  ApplicationSettingsModelState({
    required this.applicationIsInitialized,
  });

  ApplicationSettingsModelState copyWithApplicationInitialized({required bool? applicationInitialized}) =>
      ApplicationSettingsModelState(applicationIsInitialized: applicationInitialized);
}
