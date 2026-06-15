package com.example.codingtasks.prep002

import kotlinx.coroutines.delay

/**
 * PREP TASK 002 — Fake Data Sources
 *
 * Bu dosya sana hazır verilmiş — değiştirmene gerek yok.
 *
 * Her fonksiyon gerçek bir network isteğini simüle ediyor.
 * 1000ms (1 saniye) bekleyip sabit bir String döndürüyor.
 *
 * Görevin: DashboardViewModel'de bu 3 fonksiyonu hem sırayla
 * hem de paralel çağıran metodları implemente etmek.
 */

suspend fun fetchWeather(): String {
    delay(1000L)
    return "Sunny, 24°C"
}

suspend fun fetchNews(): String {
    delay(1000L)
    return "Top story: Kotlin 2.0 released"
}

suspend fun fetchStocks(): String {
    delay(1000L)
    return "BTC: \$65,000"
}
