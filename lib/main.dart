import 'package:flutter/material.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import 'package:smart_igloo_app/smart_igloo_application/smart_igloo_application.dart';

void main() {
  AndroidOptions _getAndroidOptions() => const AndroidOptions(
    encryptedSharedPreferences: true,
  );

  runApp(const SmartIglooApp());
}

