# PREP TASK 003 — Cold Flow: Builder + Operators

## Hedef
Kotlin `flow { }` builder ile cold flow oluşturmayı, `map` / `onCompletion` gibi operatörleri
zincirde kullanmayı ve ViewModel'de bir `Job` tutarak flow'u iptal etmeyi öğrenmek.

## Senaryo
10'dan 0'a doğru sayan geri sayım sayacı.
- "Başlat" → flow başlar, her saniye sayı azalır
- "Durdur" → aktif job iptal edilir, sayım durur
- 0'a ulaşıldığında otomatik "Bitti!" mesajı çıkar

---

## Acceptance Criteria

- [ ] `countdownFlow(from: Int): Flow<Int>` fonksiyonu `flow { }` builder ile yazıldı
- [ ] Flow'un emit ettiği Int değerleri `map { }` operatörü ile `"$it saniye"` String'ine dönüştürülüyor
  - Dönüşüm ViewModel'de, toplayıcı tarafında yapılıyor (zincirde)
- [ ] ViewModel'de `private var countdownJob: Job?` tutuluyor
- [ ] `startCountdown()`: yeni bir `Job` başlatıp `countdownJob`'a atıyor
- [ ] `stopCountdown()`: `countdownJob?.cancel()` ile iptal ediyor
- [ ] `onCompletion { cause -> }` operatörü kullanılıyor — `cause == null` ise "Bitti!" state'i set ediliyor
- [ ] UI'da aktif sayım label'ı gösteriliyor ("X saniye")
- [ ] UI'da "Bitti!" mesajı gösteriliyor (tamamlandığında)
- [ ] "Başlat" ve "Durdur" butonları var

---

## İpuçları

### Cold Flow — flow Builder
```kotlin
fun countdownFlow(from: Int): Flow<Int> = flow {
    for (i in from downTo 0) {
        emit(i)           // Değeri yayınla
        delay(1000L)      // 1 saniye bekle (son değerden sonra da beklememek için sıralamayı düzenle)
    }
}
```

### map Operatörü (Dönüşüm)
```kotlin
countdownFlow(10)
    .map { seconds -> "$seconds saniye" }  // Int → String
    .collect { label -> /* UI'a yaz */ }
```

### onCompletion (Tamamlanma / İptal Algılama)
```kotlin
countdownFlow(10)
    .onCompletion { cause ->
        if (cause == null) {
            // Flow normal tamamlandı (iptal değil)
        }
    }
    .collect { ... }
```

### Job Tutarak İptal Etme
```kotlin
private var countdownJob: Job? = null

fun startCountdown() {
    countdownJob?.cancel()  // Varsa öncekini iptal et
    countdownJob = viewModelScope.launch {
        countdownFlow(10)
            .map { ... }
            .onCompletion { ... }
            .collect { ... }
    }
}

fun stopCountdown() {
    countdownJob?.cancel()
    countdownJob = null
}
```

---

## Ekstra (İsteğe Bağlı)
- Flow başlamadan önce `from` değerini parametrik yap (kullanıcı girebilsin)
- Her tik için progress bar göster
