# CodingTasks — Android Practice Repository

Bu repo, Jetpack Compose + Modern Android pratik yapma amaçlıdır. Her task bir `task-XX-konu` branch'inde yaşar.

## Proje Yapısı

```
app/src/main/java/com/example/codingtasks/
├── taskXX/              ← her task kendi package'ında
│   ├── data/            ← repository, data source, models
│   ├── domain/          ← use case'ler, domain model'lar (task karmaşıklaştıkça)
│   ├── presentation/    ← ViewModel, UiState, UiEvent
│   └── ui/              ← Composable'lar
└── core/                ← shared utilities, theme
```

## Kod Standartları

- **State yönetimi:** ViewModel'de `StateFlow<UiState>` kullan. UiState sealed class olmalı.
- **Composable'lar:** Stateless tercih et, state hoisting uygula. Her composable preview'u olmalı.
- **Coroutines:** `viewModelScope` dışında coroutine başlatma. `suspend` fonksiyon test edilebilir olmalı.
- **Naming:** ViewModel fonksiyonları `onXxx()` şeklinde. UiEvent sealed class ile.
- **Imports:** Wildcard import yok (`import androidx.compose.material3.*` yerine tek tek)

## Task Branch Workflow

```bash
# Yeni task'a geç
git checkout task-01-flow-operators

# main'i güncel tut
git checkout main && git pull

# Task tamamlandığında
git checkout main
git merge task-01-flow-operators
```

## Kullanılan Teknolojiler

- Jetpack Compose + Material3
- Kotlin Coroutines + Flow
- ViewModel + StateFlow
- (Task ilerledikçe eklenir: Room, Hilt, Paging3, Navigation)

## AI ile Çalışmak

Bu repo'da Claude Code kullanırken:
- `TASK.md` dosyası o branch'in görevini açıklar — önce onu oku
- Hint istersen "İpucu ver ama cevabı söyleme" de
- Kod review için "bu implementasyonu review et" de
- Takıldığında ilgili Android doc'u veya kaynak kodu bul
