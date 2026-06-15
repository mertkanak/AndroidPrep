package com.example.codingtasks.prep001

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * PREP TASK 001 — TrafficLightViewModel
 *
 * Bu ViewModel trafik ışığının mevcut durumunu tutar ve
 * kullanıcının "Sonraki" butonuna basmasını işler.
 */
class TrafficLightViewModel : ViewModel() {

    // TODO: _state'i başlangıç değeriyle (Red) başlat
    // İPUCU: MutableStateFlow<TrafficLightState>(...) kullan
    private val _state: MutableStateFlow<TrafficLightState> = TODO("Başlangıç state'ini ata")

    // TODO: Dışarıya sadece okunabilir StateFlow sun
    val state: StateFlow<TrafficLightState> = TODO("_state'i asStateFlow() ile sun")

    /**
     * TODO: "Sonraki" butonuna basılınca bu fonksiyon çağrılır.
     *
     * Mevcut state'i bir sonraki state'e ilerlet.
     * İPUCU: `_state.update { currentState -> currentState.next() }` kullanabilirsin
     *        ya da `_state.value = _state.value.next()` da çalışır.
     */
    fun onNextClicked() {
        TODO("State'i bir sonraki duruma ilerlet")
    }
}
