import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import me.kshulzh.farm.ui.common.App


fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
