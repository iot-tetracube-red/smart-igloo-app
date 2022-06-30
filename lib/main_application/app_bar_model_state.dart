class AppBarModelState {
  final String? title;

  const AppBarModelState({this.title});

  AppBarModelState copyWithNewTitle({String? newTitle}) =>
      AppBarModelState(title: newTitle);
}
