package com.riffle.feature.reader.ui

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.riffle.feature.designsystem.TestTags
import com.riffle.feature.reader.ui.generated.resources.Res
import com.riffle.feature.reader.ui.generated.resources.reader_double_page_prompt_body
import com.riffle.feature.reader.ui.generated.resources.reader_double_page_prompt_enable
import com.riffle.feature.reader.ui.generated.resources.reader_double_page_prompt_not_now
import com.riffle.feature.reader.ui.generated.resources.reader_double_page_prompt_title
import org.jetbrains.compose.resources.stringResource

@Composable
fun DoublePagePromptDialog(
    onEnable: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier.testTag(TestTags.READER_DOUBLE_PAGE_PROMPT),
        title = { Text(stringResource(Res.string.reader_double_page_prompt_title)) },
        text = { Text(stringResource(Res.string.reader_double_page_prompt_body)) },
        confirmButton = {
            TextButton(
                onClick = onEnable,
                modifier = Modifier.testTag(TestTags.READER_DOUBLE_PAGE_PROMPT_ENABLE),
            ) {
                Text(stringResource(Res.string.reader_double_page_prompt_enable))
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                modifier = Modifier.testTag(TestTags.READER_DOUBLE_PAGE_PROMPT_NOT_NOW),
            ) {
                Text(
                    stringResource(Res.string.reader_double_page_prompt_not_now),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        },
    )
}
