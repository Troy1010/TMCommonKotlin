package com.tminus1010.tmcommonkotlin.rx.extensions

import androidx.recyclerview.widget.LinearLayoutManager

/**
 * scrolls horizontally or vertically depending on orientation.
 */
fun LinearLayoutManager.scrollTo(pixelPosition: Int) {
    scrollToPositionWithOffset(0, -pixelPosition)
}