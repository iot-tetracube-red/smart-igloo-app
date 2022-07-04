import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:smart_igloo_app/smart_igloo_app_settings_state/smart_igloo_app_settings_service.dart';
import 'package:smart_igloo_app/smart_igloo_application/model_state_application_settings.dart';

class SmartIglooAppSettingsCubit
    extends Cubit<ApplicationSettingsModelState> {
  final smartIglooAppSettingsService = SmartIglooAppSettingsService();

  SmartIglooAppSettingsCubit()
      : super(ApplicationSettingsModelState(applicationIsInitialized: true));

  applicationInitialized() async {
    var isApplicationInitialized = await smartIglooAppSettingsService
        .isApplicationInitialized();
    emit(state.copyWithApplicationInitialized(applicationInitialized: isApplicationInitialized));
  }
}
