# PREP TASK 001 — Sealed Classes + When Expression

## Hedef
Kotlin'de sealed class ve exhaustive `when` expression kullanımını pekiştirmek.

## Senaryo
Trafik ışığı uygulaması. Işık 3 durumda olabilir: Kırmızı, Sarı, Yeşil.
"Sonraki" butonuna basıldıkça durum döngüsel olarak ilerler.

---

## Acceptance Criteria

- [ ] `TrafficLightState` sealed class tanımlandı (Red, Yellow, Green)
  - Her alt sınıf en az bir veri taşıyor: renk (Color), mesaj (String), süre (Int — saniye)
- [ ] `when` expression **exhaustive** — `else` dalı **yok**
- [ ] `TrafficLightViewModel` içinde `StateFlow<TrafficLightState>` var
- [ ] `nextState()` fonksiyonu: Red → Yellow → Green → Red döngüsü
- [ ] Compose ekranı duruma göre farklı arka plan rengi gösteriyor
- [ ] Compose ekranı duruma göre farklı mesaj gösteriyor
- [ ] `@Preview` anotasyonu mevcut

---

## İpuçları

### Sealed Class Nedir?
Sealed class, sınırlı sayıda alt sınıfa sahip olabilen bir sınıftır.
Derleyici tüm alt sınıfları bildiğinden `when` ifadesi exhaustive olabilir.

```kotlin
sealed class TrafficLightState {
    data class Red(/* ... */) : TrafficLightState()
    // ...
}
```

### Exhaustive When
```kotlin
// ✅ else YOK — derleyici tüm durumları kontrol eder
val message = when (state) {
    is TrafficLightState.Red -> state.message
    is TrafficLightState.Yellow -> state.message
    is TrafficLightState.Green -> state.message
}
```

### StateFlow ile ViewModel
```kotlin
private val _state = MutableStateFlow<TrafficLightState>(TrafficLightState.Red(...))
val state: StateFlow<TrafficLightState> = _state.asStateFlow()
```

### Composable'da StateFlow Collect
```kotlin
val state by viewModel.state.collectAsStateWithLifecycle()
```

---

## Ekstra (İsteğe Bağlı)
- Her durumun kaç saniye kaldığını gösteren geri sayım ekle
- Animasyonlu renk geçişi ekle (`animateColorAsState`)
