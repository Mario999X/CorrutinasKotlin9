import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import models.Empaquetador
import models.Fabricador

// La fabrica de yeso, caso con canales, 1 productor y muchos consumidores -> Producer / Opcion 1-1 tambien
private const val NUM_SACOS_PRODUCCION = 300

fun main(): Unit = runBlocking{

    FileController.resetFile()

    val fabricador = Fabricador("Paco", NUM_SACOS_PRODUCCION)

    //val empaquetador = Empaquetador("Felipe")

    val canal = fabricador.produceSacos()

    /*launch {
        empaquetador.preparaLotes(canal)
    }*/

    val empaquetadores = listOf(Empaquetador("Felipe"), Empaquetador("Pablo"))

    empaquetadores.forEach { e ->
        launch {
            e.preparaLotes(canal)
        }
    }
}