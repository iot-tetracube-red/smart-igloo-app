import 'package:flutter_secure_storage/flutter_secure_storage.dart';

class SmartIglooAppSettingsService {
  final FlutterSecureStorage _storage = const FlutterSecureStorage();

  Future<bool> isApplicationInitialized() async {
    return (await _storage.read(key: "application_initialized")) != null;
  }

// Read value
/*String value = await storage.read(key: key);

// Read all values
  Map<String, String> allValues = await storage.readAll();
// Write value
  await storage.write(key: key, value: value);*/
}
