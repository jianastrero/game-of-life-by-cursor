import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.MainMenu) }
    var selectedTemplate by remember { mutableStateOf<GameTemplate?>(null) }

    Window(
        onCloseRequest = ::exitApplication,
        title = "Game of Life",
        state = rememberWindowState(width = 800.dp, height = 600.dp)
    ) {
        MaterialTheme {
            when (currentScreen) {
                Screen.MainMenu -> MainMenuScreen(
                    onTemplateSelected = { template ->
                        selectedTemplate = template
                        currentScreen = Screen.Game
                    }
                )
                Screen.Game -> GameScreen(
                    template = selectedTemplate,
                    onBack = { currentScreen = Screen.MainMenu }
                )
            }
        }
    }
}

enum class Screen {
    MainMenu, Game
}
