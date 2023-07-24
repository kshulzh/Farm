import csstype.px
import csstype.rgb
import emotion.react.css
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import me.kshulzh.farm.dto.AnimalDto
import me.kshulzh.farm.http.HttpClientImpl
import react.FC
import react.Props
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.input
import react.useState

external interface WelcomeProps : Props {
    var name: String
}

val Welcome = FC<WelcomeProps> { props ->
    var name by useState(props.name)
    div {
        css {
            padding = 5.px
            backgroundColor = rgb(8, 97, 22)
            color = rgb(56, 246, 137)
        }
        +"Hello, $name"
    }
    input {
        css {
            marginTop = 5.px
            marginBottom = 5.px
            fontSize = 14.px
        }
        type = InputType.text
        value = name
        onChange = { event ->
            val httpClient = HttpClientImpl("http://localhost:8080")
            var d: AnimalDto? = null
            MainScope().launch {
                var a = httpClient.getById<AnimalDto>(path = "/animals-service/animals", id = "46")
                name = a.specieId ?: "FFFFFF"
            }.start()

            //name = event.target.value
        }
    }
}