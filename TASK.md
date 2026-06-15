# PREP TASK 005 — Compose Recomposition Debugging

## Hedef
Jetpack Compose'da gereksiz recomposition'a yol açan yaygın hataları tespit etmek ve düzeltmek.

## Senaryo
`BuggyProductList.kt` dosyası çalışan ama performans sorunları içeren bir Composable içeriyor.
Dosya kasıtlı olarak **4 hata** barındırıyor. Görevin: hataları bul, her birini açıkla ve düzelt.

---

## 4 Hata ve Düzeltmeleri

### Hata 1 — Lambda Her Recomposition'da Yeniden Oluşturuluyor
```kotlin
// HATALI: Her recomposition'da yeni lambda instance oluşturulur
// Compose, eski ve yeni lambda'yı farklı görür → gereksiz recomposition tetikler
onClick = { viewModel.onItemClick(item) }

// DOĞRU: Lambda'yı sabitle
val onItemClick: (Product) -> Unit = remember { { item -> viewModel.onItemClick(item) } }
// veya modern yaklaşım:
onClick = viewModel::onItemClick
```

### Hata 2 — Pahalı Hesaplama `remember` Olmadan
```kotlin
// HATALI: Her recomposition'da yeniden hesaplanır (N ürün varsa N iterasyon)
val total = items.sumOf { it.price }

// DOĞRU: Sadece items değiştiğinde yeniden hesapla
val total = remember(items) { items.sumOf { it.price } }
```

### Hata 3 — `derivedStateOf` Yerine Direkt Okuma
```kotlin
// HATALI: items her değiştiğinde hasItems yeniden hesaplanır
// ve bağlı tüm Composable'lar recompose olur
val hasItems = items.isNotEmpty()

// DOĞRU: derivedStateOf ile sadece sonuç değiştiğinde recompose tetikle
val hasItems by remember { derivedStateOf { items.isNotEmpty() } }
```

### Hata 4 — Unstable CartState (MutableList)
```kotlin
// HATALI: MutableList, Compose'un stability checker'ı tarafından unstable görülür
// Compose her seferinde bu state'in değişip değişmediğini bilemez → fazla recomposition
data class CartState(val items: MutableList<Product>)

// DOĞRU: Immutable List kullan
data class CartState(val items: List<Product>)
```

---

## Acceptance Criteria

- [ ] 4 hata `BuggyProductList.kt` dosyasında bulundu (yorum satırıyla işaretlenebilir)
- [ ] Lambda sabitleme yapıldı (en az `remember` veya `::` kullanıldı)
- [ ] `remember(items) { items.sumOf { it.price } }` kullanıldı
- [ ] `remember { derivedStateOf { items.isNotEmpty() } }` kullanıldı
- [ ] `CartState` immutable `List<Product>` kullanacak şekilde düzeltildi
- [ ] `RecompositionViewModel` tamamlandı

---

## İpuçları

### Recomposition Nedir?
Compose'da state değişince ilgili Composable fonksiyonu yeniden çağrılır. Bu genellikle hızlıdır,
ancak gereksiz yere tetiklenirse (özellikle büyük listelerde veya pahalı hesaplamalarda) performans sorununa yol açar.

### Layout Inspector ile Recomposition Sayısını Görme
Android Studio → Layout Inspector → Recomposition counts açılabilir.
Bir item'ın sayacı çok yüksekse gereksiz recomposition var demektir.

### Stability ve @Stable/@Immutable
- `MutableList`, `MutableMap` gibi mutable koleksiyonlar Compose tarafından unstable görülür
- `List`, `Set`, `Map` (Kotlin stdlib) da teknik olarak unstable'dır (interface)
- `@Immutable` anotasyonu ile Compose'a "bu tip değişmez" diyebilirsin
- Kotlinx Immutable Collections kütüphanesi gerçek anlamda immutable koleksiyon sağlar

---

## Görev Adımları
1. `BuggyProductList.kt`'yi oku ve 4 hatayı tespit et
2. Her hatanın yanına `// BUG X:` yorumu ekle ve hatayı açıkla
3. Her hatayı düzelt
4. `RecompositionViewModel.kt` skeleton'ını tamamla
