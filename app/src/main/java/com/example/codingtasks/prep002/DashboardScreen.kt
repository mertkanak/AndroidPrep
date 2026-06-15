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
    // TODO: viewModel.uiState'i collectAsStateWithLifecycle() ile topla
    val uiState: DashboardUiState = TODO("State'i buraya topla")

    DashboardContent(
        uiState = uiState,
        onLoadSequential = { TODO("viewModel.loadSequential() çağır") },
        onLoadParallel = { TODO("viewModel.loadParallel() çağır") }
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

        // TODO: isLoading == true iken CircularProgressIndicator göster

        // TODO: Son mod ve geçen süreyi göster
        // Örnek: "Mod: Sequential | Süre: 3024 ms"

        Spacer(modifier = Modifier.height(16.dp))

        // TODO: Hava durumu, haberler, borsa verilerini göster
        // Veriler henüz yüklenmemişse ("" ise) gösterme

        Spacer(modifier = Modifier.weight(1f))

        // TODO: İki buton yan yana — Row ile düzenle
        // Buton 1: "Sıralı Yükle" → onLoadSequential
        // Buton 2: "Paralel Yükle" → onLoadParallel
        // Her iki butonu da isLoading sırasında enabled = false yap
    }
}

/**
 * TODO: En az 1 @Preview ekle (yükleme öncesi idle durumu)
 */
// TODO: @Preview buraya
