package com.riffle.feature.reader

const val DOUBLE_PAGE_MIN_WIDTH_DP: Float = 600f

fun isDoublePageCapableWidth(widthDp: Float): Boolean = widthDp >= DOUBLE_PAGE_MIN_WIDTH_DP

fun shouldUseDoublePageSpread(
    requested: Boolean,
    isPaginated: Boolean,
    isDoublePageCapable: Boolean,
): Boolean = requested && isPaginated && isDoublePageCapable

fun shouldPromptForDoublePage(
    requested: Boolean,
    isPaginated: Boolean,
    isDoublePageCapable: Boolean,
    promptShown: Boolean,
): Boolean = !requested && !promptShown && isPaginated && isDoublePageCapable
