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
sealed class TrafficLightState {

    // TODO: Red sınıfını tamamla — uygun renk, mesaj ve süreyi ata
    data class Red(
        val color: Color = Color.Unspecified,   // TODO: Color.Red kullan
        val message: String = "",               // TODO: "DUR!" gibi bir mesaj yaz
        val durationSeconds: Int = 0            // TODO: Gerçek süreyi ata (örn. 30)
    ) : TrafficLightState()

    // TODO: Yellow sınıfını tamamla
    data class Yellow(
        val color: Color = Color.Unspecified,   // TODO: Color.Yellow kullan
        val message: String = "",               // TODO: Uygun mesaj yaz
        val durationSeconds: Int = 0            // TODO: Gerçek süreyi ata (örn. 5)
    ) : TrafficLightState()

    // TODO: Green sınıfını tamamla
    data class Green(
        val color: Color = Color.Unspecified,   // TODO: Color.Green kullan
        val message: String = "",               // TODO: Uygun mesaj yaz
        val durationSeconds: Int = 0            // TODO: Gerçek süreyi ata (örn. 25)
    ) : TrafficLightState()
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
    // TODO: Implement — when (this) { is Red -> ... }
    TODO("next() fonksiyonunu implement et")
}
