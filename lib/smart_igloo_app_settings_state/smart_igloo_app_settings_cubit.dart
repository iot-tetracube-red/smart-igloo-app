import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:smart_igloo_app/smart_igloo_app_settings_state/smart_igloo_app_settings_model_state.dart';
import 'package:smart_igloo_app/smart_igloo_app_settings_state/smart_igloo_app_settings_service.dart';

class SmartIglooAppSettingsCubit
    extends Cubit<SmartIglooAppSettingsModelState> {
  final smartIglooAppSettingsService = SmartIglooAppSettingsService();

  SmartIglooAppSettingsCubit()
      : super(SmartIglooAppSettingsModelState(applicationIsInitialized: true));

  applicationInitialized() async {
    var isApplicationInitialized = await smartIglooAppSettingsService
        .isApplicationInitialized();
    emit(state.copyWithApplicationInitialized(applicationInitialized: isApplicationInitialized));
  }
}
