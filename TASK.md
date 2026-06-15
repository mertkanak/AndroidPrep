# PREP TASK 002 — Coroutines: Sequential vs Parallel

## Hedef
Kotlin coroutines'de `suspend` fonksiyonları sırayla ve paralel çalıştırma arasındaki farkı kavramak.
`async/await` ve `awaitAll` kullanımını pekiştirmek.

## Senaryo
Dashboard ekranı — 3 farklı veri kaynağından (hava durumu, haberler, borsa) veri çekiliyor.
Kullanıcı iki moddan birini seçiyor:
- **Sıralı (Sequential):** Kaynaklar birer birer beklenerek çekilir → ~3 saniye
- **Paralel (Parallel):** Kaynaklar eş zamanlı çekilir → ~1 saniye

Ekranda hem sonuçlar hem de geçen süre gösteriliyor.

---

## Acceptance Criteria

- [ ] `loadSequential()` fonksiyonu çalışıyor ve sonuçları `_uiState`'e yazıyor
- [ ] `loadParallel()` fonksiyonu çalışıyor ve sonuçları `_uiState`'e yazıyor
- [ ] `loadParallel()` içinde `async { } + awaitAll()` **zorunlu olarak** kullanıldı
- [ ] UI'da hava durumu, haberler ve borsa verileri ayrı ayrı gösteriliyor
- [ ] Geçen süre milisaniye cinsinden ölçülüp ekranda gösteriliyor
- [ ] Yükleme sırasında loading göstergesi var

---

## İpuçları

### Sıralı Çalıştırma
```kotlin
// Her satır bir öncekinin bitmesini bekler
val weather = fetchWeather()   // 1 sn bekle
val news    = fetchNews()      // 1 sn daha bekle
val stocks  = fetchStocks()    // 1 sn daha bekle
// Toplam: ~3 saniye
```

### Paralel Çalıştırma — async + awaitAll
```kotlin
// Hepsi aynı anda başlar
val weatherDeferred = async { fetchWeather() }
val newsDeferred    = async { fetchNews() }
val stocksDeferred  = async { fetchStocks() }

val results = awaitAll(weatherDeferred, newsDeferred, stocksDeferred)
// Toplam: ~1 saniye
```

### Süre Ölçümü
```kotlin
val startTime = System.currentTimeMillis()
// ... işlem ...
val elapsed = System.currentTimeMillis() - startTime
```

### ViewModel'de coroutine başlatma
```kotlin
viewModelScope.launch {
    // suspend fonksiyonları burada çağır
}
```

---

## Ekstra (İsteğe Bağlı)
- Hata durumunu ele al: bir kaynak başarısız olursa ne olsun?
- `async` yerine `coroutineScope { }` bloğu içinde paralel çalıştırma dene
