package com.riffle.feature.reader

import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class DoublePageLayoutTest {

    @Test
    fun `compact phone width is not double-page capable`() {
        assertFalse(isDoublePageCapableWidth(393f))
        assertFalse(isDoublePageCapableWidth(599.9f))
    }

    @Test
    fun `breakpoint is inclusive`() {
        assertTrue(isDoublePageCapableWidth(600f))
        assertTrue(isDoublePageCapableWidth(673f))
        assertTrue(isDoublePageCapableWidth(1024f))
    }

    @Test
    fun `spread requires the user setting, pagination and a wide window`() {
        assertTrue(shouldUseDoublePageSpread(requested = true, isPaginated = true, isDoublePageCapable = true))
        assertFalse(shouldUseDoublePageSpread(requested = false, isPaginated = true, isDoublePageCapable = true))
        assertFalse(shouldUseDoublePageSpread(requested = true, isPaginated = false, isDoublePageCapable = true))
        assertFalse(shouldUseDoublePageSpread(requested = true, isPaginated = true, isDoublePageCapable = false))
    }

    @Test
    fun `prompt only fires once on a wide paginated window while the setting is off`() {
        assertTrue(
            shouldPromptForDoublePage(
                requested = false,
                isPaginated = true,
                isDoublePageCapable = true,
                promptShown = false,
            ),
        )
        assertFalse(
            shouldPromptForDoublePage(
                requested = true,
                isPaginated = true,
                isDoublePageCapable = true,
                promptShown = false,
            ),
        )
        assertFalse(
            shouldPromptForDoublePage(
                requested = false,
                isPaginated = false,
                isDoublePageCapable = true,
                promptShown = false,
            ),
        )
        assertFalse(
            shouldPromptForDoublePage(
                requested = false,
                isPaginated = true,
                isDoublePageCapable = false,
                promptShown = false,
            ),
        )
        assertFalse(
            shouldPromptForDoublePage(
                requested = false,
                isPaginated = true,
                isDoublePageCapable = true,
                promptShown = true,
            ),
        )
    }
}
