package com.example.codingtasks.prep003

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
 * PREP TASK 003 — CountdownScreen
 */
@Composable
fun CountdownScreen(
    viewModel: CountdownViewModel = viewModel()
) {
    // TODO: viewModel.uiState'i collectAsStateWithLifecycle() ile topla
    val uiState: CountdownUiState = TODO("State'i buraya topla")

    CountdownContent(
        uiState = uiState,
        onStart = { TODO("viewModel.startCountdown() çağır") },
        onStop = { TODO("viewModel.stopCountdown() çağır") }
    )
}

/**
 * TODO: Bu Composable'ı tamamla.
 *
 * Göstermesi gerekenler:
 *   1. Ortada büyük sayı/label → uiState.displayLabel
 *      - Normal sayım: "X saniye" (büyük font, örn. 72.sp)
 *      - Bitince: "Bitti!" (farklı renk veya stil)
 *   2. "Başlat" butonu
 *      - enabled = !uiState.isRunning
 *      - onClick = onStart
 *   3. "Durdur" butonu
 *      - enabled = uiState.isRunning
 *      - onClick = onStop
 *
 * İPUCU: isFinished durumunu farklı bir renk veya Text ile belirt
 */
@Composable
fun CountdownContent(
    uiState: CountdownUiState,
    onStart: () -> Unit,
    onStop: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // TODO: displayLabel'ı büyük fontla göster
        // İPUCU: isFinished ise farklı renk kullan (örn. MaterialTheme.colorScheme.primary)

        Spacer(modifier = Modifier.height(48.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // TODO: "Başlat" butonu — enabled = !uiState.isRunning
            // TODO: "Durdur" butonu — enabled = uiState.isRunning
        }
    }
}

/**
 * TODO: En az 1 @Preview ekle
 */
// TODO: @Preview buraya
