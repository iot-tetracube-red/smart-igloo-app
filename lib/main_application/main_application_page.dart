import 'dart:math';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:smart_igloo_app/main_application/app_bar_model_state.dart';
import 'package:flutter_gen/gen_l10n/app_localizations.dart';

import 'app_bar_cubit.dart';

class MainApplicationPage extends StatelessWidget {
  const MainApplicationPage({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).scaffoldBackgroundColor,
        shape: Border(
            bottom: BorderSide(
                color: Theme.of(context).colorScheme.onBackground,
                width: 0.1
            )
        ),
        systemOverlayStyle:
        const SystemUiOverlayStyle(statusBarColor: Colors.transparent),
        centerTitle: true,
        title: BlocBuilder<AppBarCubit, AppBarModelState>(
          builder: (ctx, state) => Text(state.title ??
              AppLocalizations.of(context)!.application_main_title),
        ),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'You have pushed the button this many times:',
            ),
            BlocBuilder<AppBarCubit, AppBarModelState>(
              builder: (ctx, state) => Text(
                state.title ?? AppLocalizations.of(context)!.application_main_title,
                style: Theme.of(context).textTheme.headline4,
              ),
            )
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: () => context.read<AppBarCubit>().setAppBarTitle(
            String.fromCharCodes(
                List.generate(23, (index) => Random().nextInt(33) + 89))),
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ),
    );
  }
}