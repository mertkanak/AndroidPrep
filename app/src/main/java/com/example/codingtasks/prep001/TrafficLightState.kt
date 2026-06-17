package com.example.codingtasks.prep001

import androidx.compose.ui.graphics.Color

/**
 * PREP TASK 001 — Sealed Classes + When Expression
 *
 * TODO: Aşağıdaki sealed class'ı tamamla.
 *
 * Her alt sınıf şu verileri taşımalı:
 *   - color: Color         → Compose'da arka plan rengi için
 *   - message: String      → Ekranda gösterilecek metin
 *   - durationSeconds: Int → Bu ışığın kaç saniye süreceği
 *
 * Şu an her sınıf placeholder değerler içeriyor. Gerçek değerleri sen dolduracaksın.
 */
sealed class TrafficLightState(
    val color: Color,
    val message: String,
    val durationSeconds: Int
) {

    // TODO: Red sınıfını tamamla — uygun renk, mesaj ve süreyi ata
    data object Red : TrafficLightState(Color.Red, "DUR",5)


    // TODO: Yellow sınıfını tamamla
    data object Yellow : TrafficLightState(Color.Yellow,"HAZIRLAN",2)

    // TODO: Green sınıfını tamamla
    data object Green : TrafficLightState(Color.Green,"GEÇ",3)
}

/**
 * TODO: Bu fonksiyonu tamamla.
 *
 * Mevcut durumu alıp bir sonraki durumu döndürmeli:
 *   Red → Yellow → Green → Red (döngüsel)
 *
 * İPUCU: `when` expression kullan, `else` KULLANMA.
 * Derleyici tüm durumları kapsadığından emin olacak.
 */
fun TrafficLightState.next(): TrafficLightState {
    return when (this) {
        is TrafficLightState.Red -> TrafficLightState.Yellow
        is TrafficLightState.Yellow -> TrafficLightState.Green
        is TrafficLightState.Green -> TrafficLightState.Red
    }
}
