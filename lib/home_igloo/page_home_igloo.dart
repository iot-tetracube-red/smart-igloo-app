import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:smart_igloo_app/home_page/smart_home_page.dart';
import 'package:smart_igloo_app/main_application/app_bar_model_state.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:smart_igloo_app/smart_igloo_application/cubit_app_bar.dart';

class HomeIglooPage extends StatelessWidget {
  const HomeIglooPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          backgroundColor: Theme.of(context).scaffoldBackgroundColor,
          shape: Border(
              bottom: BorderSide(
                  color: Theme.of(context).colorScheme.onBackground,
                  width: 0.1)),
          systemOverlayStyle:
              const SystemUiOverlayStyle(statusBarColor: Colors.transparent),
          centerTitle: true,
          title: BlocBuilder<AppBarCubit, AppBarModelState>(
            builder: (ctx, state) => Text(state.title ??
                AppLocalizations.of(context)!.application_main_title),
          ),
        ),
        body: const SmartHomePage());
  }
}
