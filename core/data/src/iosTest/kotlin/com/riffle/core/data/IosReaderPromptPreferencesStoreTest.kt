package com.riffle.core.data

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import platform.Foundation.NSUserDefaults
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class IosReaderPromptPreferencesStoreTest {

    @BeforeTest
    fun clear() = NSUserDefaults.standardUserDefaults.removeObjectForKey(KEY_READER_DOUBLE_PAGE_PROMPT_SHOWN)

    @AfterTest
    fun cleanup() = NSUserDefaults.standardUserDefaults.removeObjectForKey(KEY_READER_DOUBLE_PAGE_PROMPT_SHOWN)

    @Test
    fun `defaults to not shown`() = runTest {
        assertFalse(ReaderPromptPreferencesStore().doublePagePromptShown.first())
    }

    @Test
    fun `marking shown is observable without a restart`() = runTest {
        val store = ReaderPromptPreferencesStore()

        store.markDoublePagePromptShown()

        assertTrue(store.doublePagePromptShown.first())
    }

    @Test
    fun `shown state persists across store instances`() = runTest {
        ReaderPromptPreferencesStore().markDoublePagePromptShown()

        assertTrue(ReaderPromptPreferencesStore().doublePagePromptShown.first())
    }
}
