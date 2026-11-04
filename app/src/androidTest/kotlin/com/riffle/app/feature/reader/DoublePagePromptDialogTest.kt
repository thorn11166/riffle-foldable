package com.riffle.app.feature.reader

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.riffle.feature.designsystem.TestTags
import com.riffle.feature.reader.ui.DoublePagePromptDialog
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DoublePagePromptDialogTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun dialogRendersAndEnableFires() {
        var enabled = 0
        composeTestRule.setContent {
            DoublePagePromptDialog(onEnable = { enabled++ }, onDismiss = {})
        }

        composeTestRule.onNodeWithTag(TestTags.READER_DOUBLE_PAGE_PROMPT_ENABLE).assertIsDisplayed()
        composeTestRule.onNodeWithTag(TestTags.READER_DOUBLE_PAGE_PROMPT_ENABLE).performClick()

        assertEquals(1, enabled)
    }

    @Test
    fun notNowFiresDismiss() {
        var dismissed = 0
        composeTestRule.setContent {
            DoublePagePromptDialog(onEnable = {}, onDismiss = { dismissed++ })
        }

        composeTestRule.onNodeWithTag(TestTags.READER_DOUBLE_PAGE_PROMPT_NOT_NOW).performClick()

        assertEquals(1, dismissed)
    }
}
