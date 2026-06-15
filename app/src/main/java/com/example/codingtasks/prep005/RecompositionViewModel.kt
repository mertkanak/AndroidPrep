package com.example.codingtasks.prep005

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * PREP TASK 005 — RecompositionViewModel
 *
 * Bu ViewModel BuggyProductList Composable'ına veri sağlar.
 */
class RecompositionViewModel : ViewModel() {

    // TODO: _cartState'i başlangıç ürünleriyle başlat
    // Başlangıç için 5 ürün oluştur: id 1-5, farklı isimler ve fiyatlar
    // NOT: CartState'teki MutableList sorununu fark ettiysen burada
    //      List mi MutableList mi kullanman gerektiğini düşün.
    private val _cartState: MutableStateFlow<CartState> = TODO("Başlangıç CartState oluştur")

    val cartState: StateFlow<CartState> = TODO("_cartState'i asStateFlow() ile sun")

    /**
     * TODO: Ürüne tıklandığında çağrılır.
     *
     * Şimdilik sadece log at:
     *   println("Tıklandı: ${product.name}")
     *
     * Sonra ekleme/silme işlemi de yapılabilir.
     */
    fun onItemClick(product: Product) {
        TODO("Tıklanan ürünü logla")
    }
}
