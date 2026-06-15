package com.example.codingtasks.prep005

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

/**
 * PREP TASK 005 — Compose Recomposition Debugging
 *
 * Bu dosya kasıtlı olarak 4 performans hatası içeriyor.
 * Görevin: hataları bul, her birinin yanına "// BUG X: açıklama" yorum ekle,
 * sonra düzelt.
 *
 * Dosyayı silme — düzelt ve kaydet.
 */

// Hata 4 burada — aşağıdaki data class'ı incele:
data class CartState(
    val items: MutableList<Product> = mutableListOf()  // <- sorun burada
)

data class Product(val id: Int, val name: String, val price: Double)

@Composable
fun BuggyProductList(
    viewModel: RecompositionViewModel = viewModel()
) {
    val cartState by viewModel.cartState.collectAsState()
    val items = cartState.items

    // Hata 2 burada — her recomposition'da yeniden hesaplanıyor:
    val total = items.sumOf { it.price }

    // Hata 3 burada — derivedStateOf kullanılmıyor:
    val hasItems = items.isNotEmpty()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        if (hasItems) {
            Text(
                text = "Toplam: ₺%.2f".format(total),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(8.dp))
        }

        LazyColumn {
            items(items, key = { it.id }) { item ->
                ProductCard(
                    product = item,
                    // Hata 1 burada — her recomposition'da yeni lambda oluşturuluyor:
                    onClick = { viewModel.onItemClick(item) }
                )
            }
        }

        if (!hasItems) {
            Text("Sepet boş")
        }
    }
}

@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = product.name, fontWeight = FontWeight.Medium)
            Text(text = "₺%.2f".format(product.price))
        }
    }
}
