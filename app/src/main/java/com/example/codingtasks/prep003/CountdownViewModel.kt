package com.example.codingtasks.prep003

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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
    private val _uiState: MutableStateFlow<CountdownUiState> = TODO("Başlangıç state'ini ata")
    val uiState: StateFlow<CountdownUiState> = TODO("_uiState'i asStateFlow() ile sun")

    // TODO: countdownJob'u tanımla (başlangıç değeri null)
    // İPUCU: private var countdownJob: Job? = null
    private var countdownJob: Job? = TODO("Job değişkenini tanımla")

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
        TODO("Geri sayımı başlat")
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
        TODO("Job'u iptal et")
    }

    override fun onCleared() {
        super.onCleared()
        // TODO: ViewModel temizlenirken aktif job'u iptal et
    }
}
