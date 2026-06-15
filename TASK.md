# PREP TASK 004 — StateFlow vs SharedFlow

## Hedef
`StateFlow` ve `SharedFlow` arasındaki farkı kavramak ve doğru senaryoda doğru flow tipini kullanmak.

## Senaryo
Alışveriş sepeti uygulaması.
- Sepet içeriği `StateFlow<List<Product>>` ile tutulur (kalıcı durum)
- "Ürün eklendi!", "Ürün silindi!" gibi tek seferlik bildirimler `SharedFlow<String>` ile yayınlanır
- Composable'da bu bildirimler `Snackbar` ile gösterilir

---

## StateFlow vs SharedFlow Farkı

### StateFlow
- Her zaman bir **mevcut değeri** vardır (başlangıç değeri zorunlu)
- Yeni subscriber anında **son değeri** alır (replay = 1, değiştirilemez)
- Aynı değer tekrar emit edilirse subscriber **bildirilmez** (distinctUntilChanged)
- Kullanım: UI state, formun mevcut durumu, sepet içeriği gibi **kalıcı veriler**

```kotlin
private val _cartItems = MutableStateFlow<List<Product>>(emptyList())
val cartItems: StateFlow<List<Product>> = _cartItems.asStateFlow()
```

### SharedFlow
- Başlangıç değeri **yoktur**
- `replay` parametresi ile kaç değer cache'lenip yeni subscriber'lara gönderileceği ayarlanır
- `replay = 0` → yeni subscriber geçmiş değerleri **almaz** (one-shot event için ideal)
- Aynı değer tekrar emit edilirse subscriber **yine bildirilir**
- Kullanım: Snackbar mesajı, navigasyon eventi, tek seferlik bildirimler

```kotlin
private val _uiEvents = MutableSharedFlow<String>(replay = 0)
val uiEvents: SharedFlow<String> = _uiEvents.asSharedFlow()

// Emit etmek için (suspend context gerekir):
viewModelScope.launch { _uiEvents.emit("Ürün eklendi!") }
```

### Özet Karşılaştırma

| Özellik           | StateFlow               | SharedFlow (replay=0)  |
|-------------------|-------------------------|------------------------|
| Başlangıç değeri  | Zorunlu                 | Yok                    |
| Son değer cache   | Evet (1 değer)          | Hayır                  |
| Tekrar aynı değer | Ignore edilir           | Emit edilir            |
| Kullanım alanı    | UI State                | One-shot events        |

---

## Acceptance Criteria

- [ ] `cartItems: StateFlow<List<Product>>` ViewModel'de tanımlı
- [ ] `uiEvents: SharedFlow<String>` ViewModel'de tanımlı, `MutableSharedFlow(replay = 0)` kullanıldı
- [ ] `addProduct(product: Product)` çalışıyor — `_cartItems.update { it + product }` ile
- [ ] `removeProduct(product: Product)` çalışıyor
- [ ] Her ekleme/silme sonrası `_uiEvents.emit(...)` ile mesaj gönderiliyor
- [ ] Composable'da `LaunchedEffect(Unit) { viewModel.uiEvents.collect { /* Snackbar */ } }` var
- [ ] Snackbar Scaffold ile doğru gösteriliyor
- [ ] Başlangıç ürünleri listede görünüyor

---

## İpuçları

### StateFlow Güncelleme
```kotlin
_cartItems.update { currentList ->
    currentList + product
}
```

### SharedFlow ile Snackbar (Composable)
```kotlin
val snackbarHostState = remember { SnackbarHostState() }

LaunchedEffect(Unit) {
    viewModel.uiEvents.collect { message ->
        snackbarHostState.showSnackbar(message)
    }
}

Scaffold(
    snackbarHost = { SnackbarHost(snackbarHostState) }
) { paddingValues ->
    // içerik
}
```

---

## Ekstra (İsteğe Bağlı)
- Sepet toplam fiyatını `StateFlow` olarak hesapla
- `replay = 1` yap ve davranışın nasıl değiştiğini gözlemle (yorum satırı ile açıkla)
