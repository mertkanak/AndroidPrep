package com.example.codingtasks.prep003

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * PREP TASK 003 — Cold Flow Builder
 *
 * TODO: `countdownFlow` fonksiyonunu implement et.
 *
 * Beklenen davranış:
 *   - `from` parametresinden 0'a kadar (0 dahil) Int değerleri emit eder
 *   - Her emit arasında 1 saniye (1000L ms) bekler
 *   - Son değeri (0) emit ettikten sonra bekleme YAPMADAN tamamlanır
 *
 * ZORUNLU: `flow { }` builder kullanılmalı.
 * Diğer builder'lar (asFlow, channelFlow) KULLANILMAMALI.
 *
 * Örnek kullanım:
 *   countdownFlow(3) → emit(3), wait 1s, emit(2), wait 1s, emit(1), wait 1s, emit(0), done
 *
 * İPUCU: kotlinx.coroutines.flow.flow ve kotlinx.coroutines.delay import'larını ekle
 */
fun countdownFlow(from: Int): Flow<Int> {
    return flow {
        for (i in from downTo 0) {
            emit(i)
            if (i > 0) {
                delay(1000)
            }
        }
    }
}
