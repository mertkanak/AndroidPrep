package com.example.codingtasks.prep004

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * PREP TASK 004 — CartViewModel
 *
 * StateFlow: Sepet içeriği (kalıcı durum)
 * SharedFlow: UI bildirimleri (tek seferlik event)
 */
class CartViewModel : ViewModel() {

    // TODO: _cartItems'ı başlangıç ürünleriyle başlat
    // Başlangıç listesi: en az 3 Product içersin (örn. id=1, name="Kotlin Kitabı", price=49.99)
    // İPUCU: MutableStateFlow(listOf(Product(...), Product(...), Product(...)))
    private val _cartItems: MutableStateFlow<List<Product>> = MutableStateFlow(
        listOf(
            Product(1, "Kotlin Kitabı", 49.99),
            Product(2, "Android Studio", 39.99),
            Product(3, "Jetpack Compose", 29.99,)
        )
    )

    // TODO: Dışarıya sadece okunabilir StateFlow sun
    val cartItems: StateFlow<List<Product>> = _cartItems.asStateFlow()

    // TODO: _uiEvents'i oluştur — replay = 0 ZORUNLU
    // İPUCU: MutableSharedFlow<String>(replay = 0)
    private val _uiEvents: MutableSharedFlow<String> = MutableSharedFlow(replay = 0)

    // TODO: Dışarıya sadece okunabilir SharedFlow sun
    val uiEvents: SharedFlow<String> = _uiEvents.asSharedFlow()

    /**
     * TODO: Sepete ürün ekle.
     *
     * Adımlar:
     *   1. `_cartItems.update { it + product }` ile listeye ekle
     *   2. `viewModelScope.launch { _uiEvents.emit("${product.name} sepete eklendi!") }` ile bildir
     *
     * İPUCU: update import: kotlinx.coroutines.flow.update
     */
    fun addProduct(product: Product) {
        _cartItems.update { it + product }
        viewModelScope.launch {
            _uiEvents.emit("${product.name} sepete eklendi!")
        }
    }

    /**
     * TODO: Sepetten ürün çıkar.
     *
     * Adımlar:
     *   1. `_cartItems.update { it.filter { item -> item.id != product.id } }` ile listeden çıkar
     *   2. `viewModelScope.launch { _uiEvents.emit("${product.name} sepetten çıkarıldı.") }` ile bildir
     */
    fun removeProduct(product: Product) {
        _cartItems.update { it.filter { item -> item.id != product.id } }
        viewModelScope.launch {
            _uiEvents.emit("${product.name} sepetten çıkarıldı.")
        }
    }
}
