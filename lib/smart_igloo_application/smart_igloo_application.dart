import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:smart_igloo_app/home_igloo/page_home_igloo.dart';
import 'package:smart_igloo_app/smart_igloo_application/cubit_app_bar.dart';
import 'package:smart_igloo_app/smart_igloo_application/model_state_application_settings.dart';

import '../color_schemes.g.dart';
import '../smart_igloo_app_settings/join_hub/join_smart_igloo_hub_page.dart';
import '../smart_igloo_app_settings_state/smart_igloo_app_settings_cubit.dart';

class SmartIglooApp extends StatelessWidget {
  const SmartIglooApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MultiBlocProvider(
      providers: [
        BlocProvider(create: (_) => AppBarCubit()),
        BlocProvider(create: (_) => SmartIglooAppSettingsCubit()..applicationInitialized())
      ],
      child: MaterialApp(
        title: 'Smart Igloo',
        localizationsDelegates: const [
          AppLocalizations.delegate,
          GlobalMaterialLocalizations.delegate,
          GlobalWidgetsLocalizations.delegate,
          GlobalCupertinoLocalizations.delegate,
        ],
        supportedLocales: const [
          Locale('en', ''),
        ],
        debugShowCheckedModeBanner: false,
        theme: ThemeData(useMaterial3: true, colorScheme: lightColorScheme),
        darkTheme: ThemeData(useMaterial3: true, colorScheme: darkColorScheme),
        initialRoute: '/',
        routes: {
          '/': (context) => BlocConsumer<SmartIglooAppSettingsCubit,
              ApplicationSettingsModelState>(
                listenWhen: (context, currentState) =>
                    !(currentState.applicationIsInitialized ?? false),
                listener: (context, currentState) => showDialog<String>(
                  context: context,
                  barrierDismissible: false,
                  builder: (BuildContext context) => AlertDialog(
                    title: const Text('AlertDialog Title'),
                    content: const Text('AlertDialog description'),
                    actions: <Widget>[
                      TextButton(
                        onPressed: () => Navigator.popAndPushNamed(context, '/settings/join_hub'),
                        child: const Text('OK'),
                      ),
                    ],
                  ),
                ),
                buildWhen: (_, currentState) =>
                    currentState.applicationIsInitialized ?? true,
                builder: (context, state) => const HomeIglooPage(),
              ),
          '/settings/join_hub': (context) => const JoinSmartIglooHubPage(),
        },
      ),
    );
  }
}
