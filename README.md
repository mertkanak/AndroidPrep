# Android Coding Tasks 🚀

Jetpack Compose + Modern Android geliştirme becerilerini pratikleştirmek için kişisel task repository'si.

## Kural
Her task kendi branch'inde yaşar. `main`'e merge etmeden önce tüm acceptance criteria'ları tamamla.

## Task Listesi

| # | Branch | Konu | Zorluk | Durum |
|---|--------|------|--------|-------|
| 01 | `task-01-flow-operators` | Flow Operators + Real-time Search | ⭐⭐⭐ | 🔴 Başlanmadı |

## Branch Stratejisi

```
main                    ← task index + CLAUDE.md
task-01-flow-operators  ← starter scaffold + TASK.md
task-02-xxx             ← gelecek task
```

Her task branch'i:
- `TASK.md` → gereksinimler, acceptance criteria, ipuçları
- Starter scaffold → interfaces, TODOs, boilerplate
- Seni implement eden sen yazacaksın

## Seviye Yol Haritası

### Bölüm 1: Reactive Patterns & State (Task 01-03)
- Flow operators, coroutines advanced patterns
- UiState, ViewModel architecture
- Compose state hoisting

### Bölüm 2: Architecture (Task 04-06)
- Clean Architecture katmanları
- Dependency Injection (Hilt)
- Repository + UseCase pattern

### Bölüm 3: Advanced Compose (Task 07-09)
- Custom Layout, custom Modifier
- Canvas & drawing
- Compose animation deep dive

### Bölüm 4: System Components (Task 10-12)
- Foreground Service + Notifications
- WorkManager
- Deep Links + App Links
