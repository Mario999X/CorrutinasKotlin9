package models

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.produce
import kotlin.coroutines.CoroutineContext

data class Fabricador(private val id: String, private val numSacos: Int) : CoroutineScope {

    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Default

    @OptIn(ExperimentalCoroutinesApi::class)
    fun produceSacos() = produce {
        for (i in 1..numSacos){
            val saco = Saco(i)
            println("Fabricador: $id | Saco: $i")
            send(saco)
            kotlinx.coroutines.delay(saco.peso.toLong())
        }
        println(" ---Fabricador: $id termino su jornada---")
    }
}