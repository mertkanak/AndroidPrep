package com.example.codingtasks.prep001

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * PREP TASK 001 — TrafficLightScreen
 *
 * Bu ekran trafik ışığının mevcut durumunu gösterir.
 */
@Composable
fun TrafficLightScreen(
    viewModel: TrafficLightViewModel = viewModel()
) {
    // TODO: viewModel.state'i collectAsStateWithLifecycle() ile topla
    // İPUCU: val state by viewModel.state.collectAsStateWithLifecycle()
    val state: TrafficLightState = TODO("State'i buraya topla")

    TrafficLightContent(
        state = state,
        onNextClicked = { TODO("ViewModel'in onNextClicked fonksiyonunu çağır") }
    )
}

/**
 * TODO: Bu Composable'ı tamamla.
 *
 * Göstermesi gerekenler:
 *   1. Tüm ekranı kaplayan, durumun rengine uygun arka plan
 *   2. Durumun mesajını büyük fontla ortada gösteren Text
 *   3. Durumun süresini gösteren Text ("X saniye" formatında)
 *   4. "Sonraki →" yazılı Button
 *
 * İPUCU: when (state) { is TrafficLightState.Red -> ... } kullanarak
 * her duruma özel değerleri al. ELSE KULLANMA.
 *
 * İPUCU 2: Rengi doğrudan state üzerinden okumak için smart cast gerekir.
 * Ortak bir özellik tanımlamak yerine when içinde her tip için ayrı dal yaz.
 */
@Composable
fun TrafficLightContent(
    state: TrafficLightState,
    onNextClicked: () -> Unit
) {
    // TODO: state'e göre gösterilecek renk, mesaj ve süreyi belirle
    // İPUCU: when (state) { ... } ile her durumu ele al
    val backgroundColor: Color = TODO("state'e göre rengi belirle")
    val message: String = TODO("state'e göre mesajı belirle")
    val duration: Int = TODO("state'e göre süreyi belirle")

    // TODO: UI'ı oluştur
    // Öneri: Column ile ortala, Box ile arka planı kapla
    Column(
        modifier = Modifier
            .fillMaxSize()
            // TODO: .background(backgroundColor) ekle
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // TODO: Mesajı büyük, kalın fontla göster (örn. fontSize = 48.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        // TODO: Süreyi göster ("$duration saniye" formatında)

        Spacer(modifier = Modifier.height(32.dp))

        // TODO: "Sonraki →" butonu — onClick = onNextClicked
    }
}

/**
 * TODO: Preview ekle.
 *
 * En az 1 adet @Preview tanımla. İstersen 3 durumu ayrı ayrı önizle.
 * İPUCU: TrafficLightContent'i doğrudan çağırabilirsin (ViewModel gerektirmez).
 */
// TODO: @Preview anotasyonu buraya gelecek
