package red.tetracube.smartigloo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.graphics.toArgb
import red.tetracube.smartigloo.application.SmartIglooApplication
import red.tetracube.smartigloo.ui.theme.md_theme_dark_surface
import red.tetracube.smartigloo.ui.theme.md_theme_light_surface

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (isSystemInDarkTheme()) {
                window?.statusBarColor = md_theme_dark_surface.toArgb()
            } else {
                window?.statusBarColor = md_theme_light_surface.toArgb()
            }
            SmartIglooApplication()
        }
    }
}