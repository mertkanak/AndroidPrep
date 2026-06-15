package com.example.codingtasks.prep004

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * PREP TASK 004 — CartScreen
 */
@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel()
) {
    // TODO: cartItems'ı collectAsStateWithLifecycle() ile topla
    val cartItems: List<Product> = TODO("cartItems'ı buraya topla")

    // TODO: SnackbarHostState oluştur (remember ile)
    val snackbarHostState: SnackbarHostState = TODO("remember { SnackbarHostState() }")

    // TODO: LaunchedEffect ile uiEvents'i dinle ve Snackbar göster
    // İPUCU:
    // LaunchedEffect(Unit) {
    //     viewModel.uiEvents.collect { message ->
    //         snackbarHostState.showSnackbar(message)
    //     }
    // }

    // TODO: Scaffold kullan — snackbarHost parametresini doldur
    Scaffold(
        snackbarHost = { TODO("SnackbarHost(snackbarHostState) ekle") }
    ) { paddingValues ->
        CartContent(
            cartItems = cartItems,
            onRemoveProduct = { TODO("viewModel.removeProduct(it) çağır") },
            modifier = Modifier.padding(paddingValues)
        )
    }
}

/**
 * TODO: Bu Composable'ı tamamla.
 *
 * Göstermesi gerekenler:
 *   1. Ekran başlığı ("Sepetim")
 *   2. Ürün listesi (LazyColumn) — her ürün için ProductItem göster
 *   3. Sepet boşsa "Sepetiniz boş" mesajı
 */
@Composable
fun CartContent(
    cartItems: List<Product>,
    onRemoveProduct: (Product) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // TODO: "Sepetim" başlığı (MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(8.dp))

        if (cartItems.isEmpty()) {
            // TODO: "Sepetiniz boş" mesajını ortada göster
        } else {
            // TODO: LazyColumn içinde cartItems'ı ProductItem ile göster
            // İPUCU: items(cartItems, key = { it.id }) { product -> ProductItem(...) }
        }
    }
}

/**
 * TODO: Bu Composable'ı tamamla.
 *
 * Her ürün satırında:
 *   - Ürün adı ve fiyatı (name + " - ₺" + price)
 *   - Silme butonu (Icons.Default.Delete ikonu, onClick = onRemove)
 *
 * İPUCU: Row ile yatay düzenleme, weight(1f) ile name'i genişlet
 */
@Composable
fun ProductItem(
    product: Product,
    onRemove: () -> Unit
) {
    // TODO: Row içinde ürün bilgisi ve silme butonu
}

/**
 * TODO: En az 1 @Preview ekle
 */
// TODO: @Preview buraya
