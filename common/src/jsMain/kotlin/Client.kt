import kotlinx.browser.document
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.js.Proxy
import me.kshulzh.farm.api.AnimalService
import me.kshulzh.farm.dto.AnimalDto
import me.kshulzh.farm.http.HttpClientImpl
import react.create
import react.dom.client.createRoot

fun main() {
    val container = document.createElement("div")
    document.body!!.appendChild(container)

    val welcome = Welcome.create {
        name = "Kotlin/JS"
    }
    createRoot(container).render(welcome)
}