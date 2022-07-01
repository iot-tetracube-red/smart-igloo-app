import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';
import 'package:flutter_localizations/flutter_localizations.dart';
import 'package:smart_igloo_app/main_application/app_bar_cubit.dart';

import '../color_schemes.g.dart';
import '../smart_igloo_app_settings/join_hub/join_smart_igloo_hub_page.dart';
import '../smart_igloo_app_settings_state/smart_igloo_app_settings_cubit.dart';
import 'main_application_page.dart';

class SmartIglooApp extends StatelessWidget {
  const SmartIglooApp({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return MultiBlocProvider(
      providers: [
        BlocProvider(create: (_) => AppBarCubit()),
        BlocProvider(create: (_) => SmartIglooAppSettingsCubit())
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
          // When navigating to the "/" route, build the FirstScreen widget.
          '/': (context) => const MainApplicationPage(),
          // When navigating to the "/second" route, build the SecondScreen widget.
          '/settings/join_hub': (context) => const JoinSmartIglooHubPage(),
        },
      ),
    );
  }
}
