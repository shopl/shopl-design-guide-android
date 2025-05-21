package com.shopl.sdg_common.util

import androidx.compose.ui.Alignment
import androidx.compose.ui.text.TextLayoutResult
import kotlin.math.roundToInt

fun textCenterAlignment(
    textLayoutResult: TextLayoutResult?,
    lineCount: Int = 0,
): Alignment.Vertical = Alignment.Vertical { size, space ->
    val height = space - size
    val center = (height / 2f).roundToInt() // Alignment.CenterVertically 참고
    when {
        textLayoutResult == null -> center // 텍스트가 그려지지 않았다면
        textLayoutResult.lineCount <= 1 -> center // 텍스트가 한 줄 이라면
        else -> { // 텍스트가 두 줄 이상이라면
            val textHeight = textLayoutResult.size.height
            val lineTop = textLayoutResult.getLineTop(lineCount)
            val lineBottom = textLayoutResult.getLineBottom(lineCount)
            val lineCenter = lineTop + (lineBottom - lineTop) / 2
            (height - textHeight) / 2 + lineCenter.roundToInt()
        }
    }
}