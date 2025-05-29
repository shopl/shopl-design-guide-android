package com.shopl.sdg_common.ext

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle


fun String.withColor(color: Color, target: String): AnnotatedString {
    val startIndex = this.indexOf(target)
    val endIndex = startIndex + target.length
    return if (startIndex in 0..endIndex) {
        AnnotatedString(
            text = this,
            spanStyles = listOf(
                AnnotatedString.Range(
                    SpanStyle(
                        color = color,
                    ), startIndex, endIndex
                )
            )
        )
    } else {
        AnnotatedString(
            text = this,
        )
    }
}