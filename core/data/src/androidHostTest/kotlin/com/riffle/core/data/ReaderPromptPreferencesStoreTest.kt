package com.riffle.core.data

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

@OptIn(ExperimentalCoroutinesApi::class)
class ReaderPromptPreferencesStoreTest {

    @get:Rule val tmp = TemporaryFolder()

    private val testScope = TestScope(UnconfinedTestDispatcher())

    private fun newDataStore(name: String) = PreferenceDataStoreFactory.create(
        scope = testScope.backgroundScope,
        produceFile = { tmp.newFile("$name.preferences_pb") },
    )

    @Test
    fun `defaults to not shown and persists the first-run acknowledgement`() = testScope.runTest {
        val store = ReaderPromptPreferencesStore(newDataStore("reader_prompt"))

        assertFalse(store.doublePagePromptShown.first())

        store.markDoublePagePromptShown()

        assertTrue(store.doublePagePromptShown.first())
    }
}
