package com.example.codingtasks.prep003

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * UI state — geri sayım ekranında gösterilecek tüm veriler
 */
data class CountdownUiState(
    val isRunning: Boolean = false,
    val displayLabel: String = "Hazır",  // "X saniye" ya da "Bitti!" ya da "Hazır"
    val isFinished: Boolean = false
)

/**
 * PREP TASK 003 — CountdownViewModel
 */
class CountdownViewModel : ViewModel() {

    // TODO: _uiState'i başlangıç değeriyle başlat
    private val _uiState: MutableStateFlow<CountdownUiState> = MutableStateFlow(CountdownUiState())
    val uiState: StateFlow<CountdownUiState> = _uiState.asStateFlow()

    // TODO: countdownJob'u tanımla (başlangıç değeri null)
    // İPUCU: private var countdownJob: Job? = null
    private var countdownJob: Job? = null

    /**
     * TODO: Geri sayımı başlat.
     *
     * Adımlar:
     *   1. Varsa önceki job'u iptal et
     *   2. isRunning = true, isFinished = false yap
     *   3. viewModelScope.launch ile yeni bir Job başlat
     *   4. countdownFlow(10) akışını al
     *   5. .map { } ile "$it saniye" String'ine dönüştür
     *   6. .onCompletion { cause -> } ekle:
     *        - cause == null ise → displayLabel = "Bitti!", isFinished = true, isRunning = false
     *        - cause != null ise → sadece isRunning = false yap (iptal edildi)
     *   7. .collect { label -> } ile her değeri displayLabel'a yaz
     */
    fun startCountdown() {
        countdownJob?.cancel()
        _uiState.update {
            it.copy(
                isRunning = true,
                isFinished = false
            )
        }
        countdownJob = viewModelScope.launch {
            countdownFlow(10)
                .map { "$it saniye" }
                .onCompletion { cause ->
                    if (cause == null) {
                        _uiState.update {
                            it.copy(
                                displayLabel = "Bitti!",
                                isFinished = true,
                                isRunning = false
                            )
                        }
                    } else {
                        _uiState.update { it.copy(isRunning = false) }
                    }
                }
                .collect { label ->
                    _uiState.update { it.copy(displayLabel = label) }
                }
        }
    }

    /**
     * TODO: Geri sayımı durdur.
     *
     * Adımlar:
     *   1. countdownJob?.cancel()
     *   2. countdownJob = null
     *   3. isRunning = false yap
     *
     * NOT: Job iptal edilince onCompletion cause != null olur,
     *      dolayısıyla "Bitti!" gösterilmez — bu beklenen davranış.
     */
    fun stopCountdown() {
        countdownJob?.cancel()
        countdownJob = null
        _uiState.update { it.copy(isRunning = false) }
    }

    override fun onCleared() {
        super.onCleared()
        countdownJob?.cancel()
    }
}
