class SmartIglooAppSettingsModelState {
  final bool applicationIsInitialized;

  SmartIglooAppSettingsModelState({
    required this.applicationIsInitialized,
  });

  SmartIglooAppSettingsModelState copyWithApplicationInitialized({required bool applicationInitialized}) =>
      SmartIglooAppSettingsModelState(applicationIsInitialized: applicationInitialized);
}
