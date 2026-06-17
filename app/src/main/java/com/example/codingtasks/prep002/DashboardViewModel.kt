package com.example.codingtasks.prep002

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * UI state — Dashboard ekranında gösterilecek tüm veriler
 */
data class DashboardUiState(
    val isLoading: Boolean = false,
    val weather: String = "",
    val news: String = "",
    val stocks: String = "",
    val elapsedMs: Long = 0L,
    val lastMode: String = "" // "Sequential" veya "Parallel"
)

/**
 * PREP TASK 002 — DashboardViewModel
 */
class DashboardViewModel : ViewModel() {

    // TODO: _uiState'i başlangıç değeriyle (DashboardUiState()) başlat
    private val _uiState: MutableStateFlow<DashboardUiState> = MutableStateFlow(DashboardUiState())

    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    /**
     * TODO: Sıralı yükleme fonksiyonunu implement et.
     *
     * Adımlar:
     *   1. isLoading = true yap
     *   2. Süreyi ölçmeye başla (System.currentTimeMillis())
     *   3. fetchWeather(), fetchNews(), fetchStocks() fonksiyonlarını
     *      SIRAYLA çağır (her biri bir öncekinin bitmesini bekler)
     *   4. Sonuçları ve geçen süreyi state'e yaz
     *   5. isLoading = false yap
     *
     * İPUCU: viewModelScope.launch { ... } içinde çalış
     */
    fun loadSequential() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val startTime = System.currentTimeMillis()
            val weather = fetchWeather()
            val news = fetchNews()
            val stocks = fetchStocks()
            val result = (System.currentTimeMillis() - startTime)
            _uiState.update { it.copy(
                stocks = stocks,
                news = news,
                weather = weather,
                isLoading = false,
                elapsedMs = result,
                lastMode = "Sequential")
            }
        }
    }

    /**
     * TODO: Paralel yükleme fonksiyonunu implement et.
     *
     * Adımlar:
     *   1. isLoading = true yap
     *   2. Süreyi ölçmeye başla
     *   3. `async { fetchWeather() }`, `async { fetchNews() }`, `async { fetchStocks() }`
     *      ile 3 Deferred oluştur
     *   4. `awaitAll(...)` ile hepsini bekle
     *   5. Sonuçları ve geçen süreyi state'e yaz
     *   6. isLoading = false yap
     *
     * İPUCU: async kullanmak için `coroutineScope { }` veya
     *        viewModelScope.launch içinde doğrudan async çağırabilirsin.
     *        async import: kotlinx.coroutines.async
     *        awaitAll import: kotlinx.coroutines.awaitAll
     */
    fun loadParallel() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val startTime = System.currentTimeMillis()
            val weather = async { fetchWeather() }
            val news = async { fetchNews() }
            val stocks = async { fetchStocks() }

            val (weatherResult, newsResult, stocksResult) = awaitAll(weather, news, stocks)

            val elapsedMs = System.currentTimeMillis() - startTime
            _uiState.update { it.copy(
                stocks = stocksResult,
                news = newsResult,
                weather = weatherResult,
                isLoading = false,
                elapsedMs = elapsedMs,
                lastMode = "Parallel")
            }
        }
    }
}
