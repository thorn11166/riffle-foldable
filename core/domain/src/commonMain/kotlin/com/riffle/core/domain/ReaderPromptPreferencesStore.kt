package com.riffle.core.domain

import kotlinx.coroutines.flow.Flow

interface ReaderPromptPreferencesStore {
    val doublePagePromptShown: Flow<Boolean>
    suspend fun markDoublePagePromptShown()
}
