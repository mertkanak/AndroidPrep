package com.example.codingtasks.prep002

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel


/**
 * PREP TASK 002 — DashboardScreen
 */
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    DashboardContent(
        uiState = uiState,
        onLoadSequential = { viewModel.loadSequential() },
        onLoadParallel = { viewModel.loadParallel() }
    )
}

/**
 * TODO: Bu Composable'ı tamamla.
 *
 * Göstermesi gerekenler:
 *   1. Yükleme sırasında CircularProgressIndicator
 *   2. Son yükleme modunu ("Sequential" / "Parallel") göster
 *   3. Geçen süreyi göster ("Süre: X ms")
 *   4. 3 veri kaynağının sonuçlarını ayrı ayrı göster
 *   5. "Sıralı Yükle" ve "Paralel Yükle" butonları
 *
 * NOT: uiState.isLoading true iken butonları disable et
 */
@Composable
fun DashboardContent(
    uiState: DashboardUiState,
    onLoadSequential: () -> Unit,
    onLoadParallel: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Dashboard",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(16.dp))
        if (uiState.isLoading) {
           CircularProgressIndicator()
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!uiState.isLoading){
            Text(text = "Hava durumu: ${uiState.weather}")
            Text(text = "Haberler: ${uiState.news}")
            Text(text = "Borsa: ${uiState.stocks}")
            Text(text = "Geçen Zaman : ${uiState.elapsedMs}")
            Text(text = "Mode: ${uiState.lastMode}")
        }
        // TODO: Hava durumu, haberler, borsa verilerini göster
        // Veriler henüz yüklenmemişse ("" ise) gösterme

        Spacer(modifier = Modifier.weight(1f))

        // TODO: İki buton yan yana — Row ile düzenle
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = onLoadSequential, enabled = !uiState.isLoading) {
                Text("Sıralı yükle")
            }
            Button(onClick = onLoadParallel, enabled = !uiState.isLoading) {
                Text("Paralel yükle")
            }
        }
        // Buton 1: "Sıralı Yükle" → onLoadSequential
        // Buton 2: "Paralel Yükle" → onLoadParallel
        // Her iki butonu da isLoading sırasında enabled = false yap
    }
}

/**
 * TODO: En az 1 @Preview ekle (yükleme öncesi idle durumu)
 */
// TODO: @Preview buraya

@Composable
@Preview
fun DashboardContentPreview() {
    DashboardContent(
        uiState = DashboardUiState(
            isLoading = false,
            weather = "Sunny, 24°C",
            news = "Top story: Kotlin 2.0 released",
            stocks = "BTC: \$65,000",),
        onLoadParallel = {},
        onLoadSequential = {},
    )
}

@Composable
@Preview
fun DashboardContentLoadingPreview() {
    DashboardContent(
        uiState = DashboardUiState(
            isLoading = true,
            weather = "Sunny, 24°C",
            news = "Top story: Kotlin 2.0 released",
            stocks = "BTC: \$65,000",),
        onLoadParallel = {},
        onLoadSequential = {},
    )
}