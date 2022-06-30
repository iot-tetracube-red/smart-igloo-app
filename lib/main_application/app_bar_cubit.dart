import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:smart_igloo_app/main_application/app_bar_model_state.dart';

class AppBarCubit extends Cubit<AppBarModelState> {
  AppBarCubit() : super(const AppBarModelState());

  setAppBarTitle(String? title) => emit(state.copyWithNewTitle(newTitle: title));
}