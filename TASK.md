# TASK 02 — Rick & Morty Karakter Takip Uygulaması

> **Konu:** Hilt DI · Clean Architecture · Repository · UseCase · Room · Online-First · Retrofit · Gerçek API
>
> **API:** https://rickandmortyapi.com/documentation

---

## Senaryo

Bir kullanıcı **Rick and Morty** evrenindeki karakterleri keşfedebileceği bir mobil uygulama istiyor.

- Karakterleri listeleyebilsin, isme göre arayabilsin, duruma göre filtreleyebilsin
- Bir karaktere tıklayınca detaylarını görebilsin
- Beğendiği karakterleri favorileyebilsin — **internet olmasa bile** favorileri görebilsin
- Ağ hatası, 404, 500 gibi durumlarda anlamlı hata mesajları görebilsin ve retry yapabilsin

Uygulama **Single Activity**, **Hilt DI**, **Clean Architecture**, **Online-First + Room cache** stratejisiyle yazılacak.

---

## Mimari Özet

```
UI Layer         → Stateless Composables  (state sadece parametre olarak gelir)
                        ↑  collectAsStateWithLifecycle()
Presentation     → ViewModel + StateFlow<UiState>
                        ↑  use case çağırır
Domain           → UseCase  (iş mantığı burada, ViewModel'de değil)
                        ↑  repository interface'ini çağırır
Data             → Repository Impl  (network + local kararını burada verir)
                   ├── Remote: Retrofit → RickMortyApiService
                   └── Local:  Room   → CharacterDao
```

### Online-First Stratejisi

```
kullanıcı listeyi açar
    → API'ye istek gönder
    → Başarılıysa: Room'a yaz → UI'ya göster
    → Hata varsa: Room'da cache var mı?
        → Varsa: cache'i göster + "offline" uyarısı
        → Yoksa: hata ekranı göster
```

---

## API Referansı

### Karakter Listesi
```
GET https://rickandmortyapi.com/api/character?page=1&name=rick&status=alive

Response (200 OK):
{
  "info": { "count": 826, "pages": 42, "next": "...", "prev": null },
  "results": [
    {
      "id": 1,
      "name": "Rick Sanchez",
      "status": "Alive",        // "Alive" | "Dead" | "unknown"
      "species": "Human",
      "gender": "Male",
      "origin":   { "name": "Earth (C-137)", "url": "..." },
      "location": { "name": "Citadel of Ricks", "url": "..." },
      "image": "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
      "episode": [
        "https://rickandmortyapi.com/api/episode/1",
        "https://rickandmortyapi.com/api/episode/2"
      ]
    }
  ]
}

Response (404):  { "error": "There is nothing here." }
Response (5xx):  sunucu hatası
```

### Karakter Detayı
```
GET https://rickandmortyapi.com/api/character/{id}

Response (200): yukarıdaki tek bir karakter objesi
Response (404): { "error": "There is nothing here." }
```

---

## Paket Yapısı

```
task02/
├── data/
│   ├── local/
│   │   ├── RickMortyDatabase.kt       ← Room @Database
│   │   ├── CharacterEntity.kt         ← cache tablosu
│   │   ├── FavoriteEntity.kt          ← favori id'leri tablosu
│   │   └── CharacterDao.kt            ← @Dao
│   ├── remote/
│   │   ├── RickMortyApiService.kt     ← Retrofit @GET tanımları
│   │   ├── dto/
│   │   │   ├── CharacterListResponseDto.kt
│   │   │   ├── CharacterDto.kt
│   │   │   ├── InfoDto.kt
│   │   │   └── LocationDto.kt
│   │   └── ApiResponse.kt             ← sealed class: Success / HttpError / NetworkError
│   └── repository/
│       └── CharacterRepositoryImpl.kt
├── di/
│   ├── NetworkModule.kt               ← @Module @InstallIn(SingletonComponent)
│   ├── DatabaseModule.kt
│   └── RepositoryModule.kt
├── domain/
│   ├── model/
│   │   ├── Character.kt               ← domain modeli (DTO'dan farklı!)
│   │   └── CharacterStatus.kt         ← enum: ALIVE, DEAD, UNKNOWN, ALL
│   ├── repository/
│   │   └── CharacterRepository.kt     ← interface (domain katmanında)
│   └── usecase/
│       ├── GetCharactersUseCase.kt
│       ├── GetCharacterDetailUseCase.kt
│       ├── ToggleFavoriteUseCase.kt
│       └── GetFavoritesUseCase.kt
├── presentation/
│   ├── list/
│   │   ├── CharacterListViewModel.kt
│   │   ├── CharacterListUiState.kt
│   │   └── CharacterListUiEvent.kt
│   ├── detail/
│   │   ├── CharacterDetailViewModel.kt
│   │   └── CharacterDetailUiState.kt
│   └── favorites/
│       ├── FavoritesViewModel.kt
│       └── FavoritesUiState.kt
└── ui/
    ├── navigation/
    │   └── RickMortyNavGraph.kt        ← NavHost + route'lar
    ├── list/
    │   ├── CharacterListScreen.kt      ← stateless
    │   └── CharacterCard.kt
    ├── detail/
    │   └── CharacterDetailScreen.kt    ← stateless
    ├── favorites/
    │   └── FavoritesScreen.kt          ← stateless
    └── components/
        ├── StatusChip.kt
        ├── ErrorView.kt                ← retry butonlu
        └── LoadingView.kt
```

---

## Gradle Kurulumu (Bunu Sen Yapacaksın)

### `libs.versions.toml` eklemeleri:

```toml
[versions]
hilt = "2.51.1"
hiltNavigationCompose = "1.2.0"
retrofit = "2.11.0"
okhttp = "4.12.0"
room = "2.6.1"
navigation = "2.8.0"
coil = "2.7.0"
kotlinxSerializationJson = "1.7.3"

[libraries]
# Hilt
hilt-android = { group = "com.google.dagger", name = "hilt-android", version.ref = "hilt" }
hilt-compiler = { group = "com.google.dagger", name = "hilt-android-compiler", version.ref = "hilt" }
hilt-navigation-compose = { group = "androidx.hilt", name = "hilt-navigation-compose", version.ref = "hiltNavigationCompose" }

# Retrofit
retrofit-core = { group = "com.squareup.retrofit2", name = "retrofit", version.ref = "retrofit" }
retrofit-gson = { group = "com.squareup.retrofit2", name = "converter-gson", version.ref = "retrofit" }
okhttp-bom = { group = "com.squareup.okhttp3", name = "okhttp-bom", version.ref = "okhttp" }
okhttp-core = { group = "com.squareup.okhttp3", name = "okhttp" }
okhttp-logging = { group = "com.squareup.okhttp3", name = "logging-interceptor" }

# Room
room-runtime = { group = "androidx.room", name = "room-runtime", version.ref = "room" }
room-ktx = { group = "androidx.room", name = "room-ktx", version.ref = "room" }
room-compiler = { group = "androidx.room", name = "room-compiler", version.ref = "room" }

# Navigation
navigation-compose = { group = "androidx.navigation", name = "navigation-compose", version.ref = "navigation" }

# Coil
coil-compose = { group = "io.coil-kt", name = "coil-compose", version.ref = "coil" }

[plugins]
hilt = { id = "com.google.dagger.hilt.android", version.ref = "hilt" }
kotlin-kapt = { id = "org.jetbrains.kotlin.kapt", version.ref = "kotlin" }
# VEYA KSP kullanıyorsan:
ksp = { id = "com.google.devtools.ksp", version = "2.0.21-1.0.28" }
```

### `app/build.gradle.kts` eklemeleri:

```kotlin
plugins {
    // ... mevcut plugin'ler ...
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.kapt)  // Hilt için kapt gerekli
    // NOT: Room için KSP daha hızlı ama Hilt kapt istiyor,
    //      ikisini karıştırmak build sorununa yol açabilir.
    //      Tüm annotation processing için kapt kullan.
}

android {
    // ... mevcut konfigürasyon ...
    
    // İnternet izni için (burada değil, Manifest'te)
}

dependencies {
    // Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Retrofit + OkHttp
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.gson)
    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp.core)
    implementation(libs.okhttp.logging)

    // Room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)

    // Navigation
    implementation(libs.navigation.compose)

    // Coil
    implementation(libs.coil.compose)
}

// Hilt için kapt konfigürasyonu
kapt {
    correctErrorTypes = true
}
```

### AndroidManifest.xml
```xml
<!-- Application tag'ini güncelle: -->
<application
    android:name=".RickMortyApp"   ← HiltApplication için custom Application class
    ...>

<!-- İzin ekle: -->
<uses-permission android:name="android.permission.INTERNET" />
```

---

## Acceptance Criteria

### 1. Proje Altyapısı
- [ ] `RickMortyApp.kt` oluşturuldu (`@HiltAndroidApp` annotasyonu var)
- [ ] `MainActivity` `@AndroidEntryPoint` aldı
- [ ] Proje build hatası olmadan derleniyor

### 2. Network Katmanı
- [ ] `ApiResponse<T>` sealed class doğru tanımlandı:
  ```kotlin
  sealed class ApiResponse<out T> {
      data class Success<T>(val data: T) : ApiResponse<T>()
      data class HttpError(val code: Int, val message: String) : ApiResponse<Nothing>()
      data class NetworkError(val exception: Exception) : ApiResponse<Nothing>()
  }
  ```
- [ ] `RickMortyApiService` Retrofit interface'i tanımlandı
- [ ] `NetworkModule` `@Singleton` scope'da Retrofit ve OkHttp provide ediyor
- [ ] OkHttp `HttpLoggingInterceptor` sadece `BuildConfig.DEBUG` modunda ekleniyor
- [ ] HTTP hataları (4xx, 5xx) try/catch içinde `ApiResponse.HttpError` olarak yakalanıyor
- [ ] `IOException` → `ApiResponse.NetworkError` olarak yakalanıyor

### 3. Local Katmanı (Room)
- [ ] `CharacterEntity` gerekli tüm alanlarla oluşturuldu
- [ ] `FavoriteEntity` sadece `id` ve `savedAt: Long` tutuyor
- [ ] `CharacterDao` şu operasyonları destekliyor:
  - `insertCharacters(List<CharacterEntity>)` - upsert stratejisi (`OnConflictStrategy.REPLACE`)
  - `getCharactersByPage(page: Int): List<CharacterEntity>`
  - `getCharacterById(id: Int): CharacterEntity?`
  - `isFavorite(id: Int): Flow<Boolean>` — reaktif!
  - `getAllFavorites(): Flow<List<CharacterEntity>>` — reaktif!
  - `insertFavorite(FavoriteEntity)` / `deleteFavorite(id: Int)`
- [ ] `RickMortyDatabase` doğru `exportSchema = true` ile tanımlandı

### 4. Repository
- [ ] `CharacterRepository` interface domain katmanında, impl data katmanında
- [ ] `getCharacters(page, name, status)` → online-first:
  1. API'ye istek at
  2. Başarıysa Room'a yaz, `ApiResponse.Success` dön
  3. Hata varsa Room'a bak, cache varsa `ApiResponse.Success(cached)` dön
  4. Cache de yoksa `ApiResponse.HttpError` / `ApiResponse.NetworkError` dön
- [ ] `getCharacterDetail(id)` → önce Room'a bak, yoksa API'ye git
- [ ] `toggleFavorite(character)` → sadece Room operasyonu
- [ ] `getFavorites()` → `Flow<List<Character>>` döner (Room Flow)

### 5. Hilt Modülleri
- [ ] `NetworkModule`: `@InstallIn(SingletonComponent::class)` — OkHttp, Retrofit, ApiService
- [ ] `DatabaseModule`: `@InstallIn(SingletonComponent::class)` — Room, Dao'lar
- [ ] `RepositoryModule`: `@InstallIn(SingletonComponent::class)` — `@Binds` ile interface → impl bağlantısı
- [ ] Tüm ViewModel'ler `@HiltViewModel` + `@Inject constructor` kullanıyor

### 6. UseCase'ler
- [ ] `GetCharactersUseCase` → sayfalama + filtre parametresi alıyor
- [ ] `GetCharacterDetailUseCase` → sadece id alıyor
- [ ] `ToggleFavoriteUseCase` → mevcut favori durumuna göre ekle/çıkar
- [ ] `GetFavoritesUseCase` → `Flow<List<Character>>` döndürüyor
- [ ] UseCase'ler `operator fun invoke()` pattern kullanıyor

### 7. Presentation (ViewModel + UiState)
- [ ] Her ekranın kendi sealed `UiState` class'ı var:
  ```kotlin
  // Örnek: CharacterListUiState
  sealed class CharacterListUiState {
      object Loading : CharacterListUiState()
      data class Success(
          val characters: List<Character>,
          val hasNextPage: Boolean,
          val isFromCache: Boolean  // ← offline moddaysa "Önbellek gösteriliyor" uyarısı
      ) : CharacterListUiState()
      data class Error(
          val message: String,
          val isNetworkError: Boolean  // ← "İnternet bağlantısı yok" vs "Sunucu hatası"
      ) : CharacterListUiState()
      object Empty : CharacterListUiState()  // ← arama sonucu boşsa
  }
  ```
- [ ] `CharacterListViewModel` search query'yi `StateFlow<String>` olarak tutuyor
- [ ] Search query debounce (500ms) Flow operator'larıyla yapılıyor
- [ ] Sayfalama: `currentPage` ViewModel'de tutuluyor, `loadMore()` fonksiyonu var
- [ ] `FavoritesUiState` Room Flow'dan güncelleniyor (manuel refresh yok!)

### 8. UI ve Compose
- [ ] `RickMortyNavGraph.kt` içinde NavHost ve 3 route tanımlandı:
  - `characters` — liste ekranı
  - `characters/{characterId}` — detay ekranı
  - `favorites` — favoriler ekranı
- [ ] BottomNavigationBar var, aktif tab highlight'lanıyor
- [ ] `CharacterListScreen` **stateless**: tüm state ViewModel'den geliyor, composable kendi state tutmuyor
- [ ] Search bar için `remember { mutableStateOf("") }` DEĞİL — search state ViewModel'de
- [ ] Scroll pozisyonu için `rememberLazyListState()` — bu doğru `remember` kullanımı
- [ ] `CharacterCard` detaya navigate ediyor, favori butonu var
- [ ] `ErrorView` retry butonu içeriyor ve callback alıyor
- [ ] `StatusChip` karakter durumuna göre renkleniyor:
  - Alive → Yeşil
  - Dead → Kırmızı
  - Unknown → Gri
- [ ] Karakter resmi Coil ile yükleniyor, `placeholder` ve `error` composable tanımlı
- [ ] Her composable'ın `@Preview`'u var

### 9. Hata Yönetimi (Gerçek Hayat Kriterleri)
- [ ] HTTP 404 → "Karakter bulunamadı" mesajı
- [ ] HTTP 5xx → "Sunucu hatası, lütfen daha sonra tekrar deneyin"
- [ ] `IOException` (no network) → "İnternet bağlantısı yok. Önbellek gösteriliyor." (cache varsa) veya "İnternet bağlantısı yok" (cache yoksa)
- [ ] Loading sırasında tüm interactive elementler disabled
- [ ] Retry butonuna basınca `currentPage = 1` reset'leniyor ve yeniden istek atılıyor

---

## Gerçek Hayat Notları (Bunları Araştır ve Anla)

### ApiResponse neden ayrı bir wrapper?
Retrofit 200 olmayan response'ları default olarak exception fırlatır.
Ama bazı API'ler `{ "success": false, "error": "..." }` döner (200 OK ama içerik hatalı).
`ApiResponse<T>` her iki durumu da temiz handle eder.

**Araştır:** Retrofit'te `Response<T>` kullanarak HTTP code'u nasıl okursun?
```kotlin
// RickMortyApiService'de retrofit2.Response<T> kullan:
@GET("character")
suspend fun getCharacters(...): Response<CharacterListResponseDto>

// Repository'de:
val response = apiService.getCharacters(...)
if (response.isSuccessful) {
    ApiResponse.Success(response.body()!!)
} else {
    ApiResponse.HttpError(response.code(), response.message())
}
```

### @Binds vs @Provides — Ne zaman hangisi?
- `@Provides` → harici kütüphane sınıflarını (Retrofit, OkHttpClient) inject ederken
- `@Binds` → kendi yazdığın interface → impl bağlantısı için (daha az boilerplate)

```kotlin
// RepositoryModule.kt
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindCharacterRepository(
        impl: CharacterRepositoryImpl
    ): CharacterRepository
}
```

### remember vs StateFlow — Nerede ne kullanılır?
```
remember { mutableStateOf(...) }
    → Sadece o composable'ın yaşam döngüsüyle bağlı UI-local state
    → Örnekler: dialog açık/kapalı, dropdown expanded, scroll pozisyonu
    → ViewModel restart edilirse sıfırlanır (bu istenen davranış!)

StateFlow (ViewModel'de)
    → Ekran rotasyonunda, process death'te yaşayan business state
    → Örnekler: yüklenen veri, search query, seçili filtre
    → Test edilebilir (ViewModel unit test'i)
```

### Online-First vs Offline-First farkı nedir?
- **Offline-First:** Room her zaman tek source of truth. Network sadece Room'u günceller. UI sadece Room'u okur.
- **Online-First (bu task):** Network primary source. Room sadece cache ve favoriler için. Network hata verince fallback olarak Room devreye girer.

Bu task'ta online-first kullanıyoruz çünkü karakter verisinin güncel olması önemli, favoriler ise offline da çalışmalı.

### Room Flow neden güçlü?
```kotlin
// DAO'da:
@Query("SELECT * FROM favorites")
fun getAllFavorites(): Flow<List<FavoriteEntity>>

// ViewModel'de (Manuel refresh YOK!):
val favoritesFlow = getFavoritesUseCase()
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

// Kullanıcı favori eklediğinde/çıkardığında Room otomatik emit eder.
// ViewModel bunu dinler, UI otomatik güncellenir.
```

### Sayfalama — Neden Paging3 değil?
Bu task'ta Paging3 kullanmıyoruz çünkü:
1. Hilt + Retrofit + Room yeterince yeni konsept var
2. Manuel sayfalama mantığını anlamak daha öğretici
3. Paging3 ayrı bir task konusu (Task 04-06 arası)

Manuel sayfalama:
```kotlin
// ViewModel'de:
private var currentPage = 1
private var hasNextPage = true

fun loadMore() {
    if (!hasNextPage || uiState.value is Loading) return
    currentPage++
    loadCharacters()
}
```

---

## Adım Adım Nereden Başlayacaksın?

1. **Gradle kurulumu** — `libs.versions.toml` + `build.gradle.kts` değişiklikleri
2. **Manifest** — INTERNET izni + custom Application class
3. **Domain modeli** — `Character.kt`, `CharacterStatus.kt` (en saf katman, bağımlılık yok)
4. **Repository interface** — `CharacterRepository.kt`
5. **DTO'lar** — `CharacterDto.kt`, `CharacterListResponseDto.kt`
6. **ApiResponse** — `ApiResponse.kt` sealed class
7. **Retrofit service** — `RickMortyApiService.kt`
8. **Room** — `CharacterEntity.kt`, `FavoriteEntity.kt`, `CharacterDao.kt`, `RickMortyDatabase.kt`
9. **NetworkModule** — Hilt module, OkHttp + Retrofit kurulumu
10. **DatabaseModule** — Hilt module, Room kurulumu
11. **RepositoryImpl** — Online-first mantığını burada yaz
12. **RepositoryModule** — `@Binds` ile bağla
13. **UseCase'ler** — birer birer implement et
14. **ViewModel'ler** — UiState sealed class'larıyla birlikte
15. **UI** — Navigation → Screens → Components sıralamasıyla

---

## Takıldığın Noktalarda Şunu De

- `"Hilt @Binds nasıl çalışıyor? İpucu ver ama kodu yazma"`
- `"ApiResponse pattern'ini anlamak istiyorum, açıkla"`
- `"Room DAO'mda Flow kullanımı doğru mu? Review et"`
- `"Bu RepositoryImpl'i review et, online-first mantığım doğru mu?"`
