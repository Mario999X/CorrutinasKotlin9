package models

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.delay
import kotlin.coroutines.CoroutineContext

data class Empaquetador(private val id: String) : CoroutineScope {

    // Forma rapida de preparar el scope
    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Default

    private var contLote = 1
    private var tiempoProduccion = 0

    private var sacos = mutableListOf<Saco>()

    private var file = FileController.init()

    suspend fun preparaLotes(canal: ReceiveChannel<Saco>) {
        for (saco in canal) {
            saco.numLote = contLote
            tiempoProduccion += saco.peso
            println("\t -Empaquetador: $id | Recoge -> $saco")
            sacos.add(saco)
            if (sacos.size == 10){
                println("\t -Empaquetador: $id | Envia lote $contLote -> $sacos")
                file.appendText("Lote: $contLote | Empaquetador: $id \n$sacos\n")
                sacos.clear()
                contLote++
                println("\t -Empaquetador: $id | Tiempo de produccion: $tiempoProduccion ms")
                delay(tiempoProduccion.toLong())
                tiempoProduccion = 0
            }
        }
        println("\t -Empaquetador: $id termino su jornada")
    }
}
