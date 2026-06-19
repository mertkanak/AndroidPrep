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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * PREP TASK 004 — CartScreen
 */
@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel()
) {
    val cartItems by viewModel.cartItems.collectAsStateWithLifecycle()

    val snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvents.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        CartContent(
            cartItems = cartItems,
            onRemoveProduct = { product -> viewModel.removeProduct(product) },
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
        Text(
            "Sepetim",
            fontSize = 48.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary)

        Spacer(modifier = Modifier.height(8.dp))

        if (cartItems.isEmpty()) {
            Text("Sepetiniz boş", fontSize = 42.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,)
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(cartItems, key = { it.id }) { item ->
                    ProductItem(product = item, onRemove = { onRemoveProduct(item) })
                }
                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
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
   Row(
       modifier = Modifier.fillMaxWidth(),
       verticalAlignment = Alignment.CenterVertically
   ) {
       Column(modifier = Modifier.weight(1f)) {
           Text(text = product.name, style = MaterialTheme.typography.bodyMedium)
           Text(text = "${product.price} ₺", style = MaterialTheme.typography.bodyMedium)
       }
       Button(onClick = onRemove) {
           Text("Ürünü Sil")
       }
   }
}

/**
 * TODO: En az 1 @Preview ekle
 */
// TODO: @Preview buraya
