package com.example.codingtasks.prep001

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * PREP TASK 001 — TrafficLightViewModel
 *
 * Bu ViewModel trafik ışığının mevcut durumunu tutar ve
 * kullanıcının "Sonraki" butonuna basmasını işler.
 */
class TrafficLightViewModel : ViewModel() {

    // TODO: _state'i başlangıç değeriyle (Red) başlat
    // İPUCU: MutableStateFlow<TrafficLightState>(...) kullan
    private val _state: MutableStateFlow<TrafficLightState> = MutableStateFlow(TrafficLightState.Red)

    // TODO: Dışarıya sadece okunabilir StateFlow sun
    val state: StateFlow<TrafficLightState> = _state.asStateFlow()

    /**
     * TODO: "Sonraki" butonuna basılınca bu fonksiyon çağrılır.
     *
     * Mevcut state'i bir sonraki state'e ilerlet.
     * İPUCU: `_state.update { currentState -> currentState.next() }` kullanabilirsin
     *        ya da `_state.value = _state.value.next()` da çalışır.
     */
    fun onNextClicked() {
        _state.update { currentState -> currentState.next() }
    }
}
