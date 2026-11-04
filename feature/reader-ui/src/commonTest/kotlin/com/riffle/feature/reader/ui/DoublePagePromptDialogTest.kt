package com.riffle.feature.reader.ui

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.runComposeUiTest
import com.riffle.feature.designsystem.TestTags
import kotlin.test.Test
import kotlin.test.assertEquals

class DoublePagePromptDialogTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun dialogIsDisplayed() = runComposeUiTest {
        setContent {
            DoublePagePromptDialog(onEnable = {}, onDismiss = {})
        }
        onNodeWithTag(TestTags.READER_DOUBLE_PAGE_PROMPT_ENABLE).assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun enableButtonFiresOnEnable() = runComposeUiTest {
        var enabled = 0
        setContent {
            DoublePagePromptDialog(onEnable = { enabled++ }, onDismiss = {})
        }
        onNodeWithTag(TestTags.READER_DOUBLE_PAGE_PROMPT_ENABLE).performClick()
        assertEquals(1, enabled)
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun notNowButtonFiresOnDismiss() = runComposeUiTest {
        var dismissed = 0
        setContent {
            DoublePagePromptDialog(onEnable = {}, onDismiss = { dismissed++ })
        }
        onNodeWithTag(TestTags.READER_DOUBLE_PAGE_PROMPT_NOT_NOW).performClick()
        assertEquals(1, dismissed)
    }
}
