package com.example.codingtasks.prep004

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val _cartItems: MutableStateFlow<List<Product>> = TODO("Başlangıç ürün listesiyle başlat")

    // TODO: Dışarıya sadece okunabilir StateFlow sun
    val cartItems: StateFlow<List<Product>> = TODO("_cartItems'ı asStateFlow() ile sun")

    // TODO: _uiEvents'i oluştur — replay = 0 ZORUNLU
    // İPUCU: MutableSharedFlow<String>(replay = 0)
    private val _uiEvents: MutableSharedFlow<String> = TODO("MutableSharedFlow(replay=0) ile oluştur")

    // TODO: Dışarıya sadece okunabilir SharedFlow sun
    val uiEvents: SharedFlow<String> = TODO("_uiEvents'i asSharedFlow() ile sun")

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
        TODO("Ürünü sepete ekle ve event emit et")
    }

    /**
     * TODO: Sepetten ürün çıkar.
     *
     * Adımlar:
     *   1. `_cartItems.update { it.filter { item -> item.id != product.id } }` ile listeden çıkar
     *   2. `viewModelScope.launch { _uiEvents.emit("${product.name} sepetten çıkarıldı.") }` ile bildir
     */
    fun removeProduct(product: Product) {
        TODO("Ürünü sepetten çıkar ve event emit et")
    }
}
