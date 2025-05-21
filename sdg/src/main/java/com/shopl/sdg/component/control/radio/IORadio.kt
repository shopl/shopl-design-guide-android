package com.shopl.sdg.component.control.radio

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.IOText
import kotlin.math.roundToInt


@Composable
fun IORadio(
    isChecked: Boolean = false,
    isEnabled: Boolean = true,
    checkedTextColor: Color = SDGColor.Neutral700,
    margin: PaddingValues = PaddingValues(),
    text: String = "",
    onClick: (() -> Unit)? = null,
) {
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val alignment = remember(textLayoutResult) {
        textCenterAlignment(textLayoutResult, lineCount = 0)
    }

    val radioBackgroundColor by animateColorAsState(
        targetValue = when (isEnabled) {
            true -> if (isChecked) SDGColor.Primary300 else SDGColor.Neutral200
            false -> SDGColor.Neutral200
        }, label = "radioBackgroundColor"
    )

    val textColor by animateColorAsState(
        targetValue = when (isEnabled) {
            true -> if (isChecked) checkedTextColor else SDGColor.Neutral700
            false -> SDGColor.Neutral300
        }, label = "textColor"
    )

    Row(
        modifier = Modifier
            .padding(margin)
            .clickable(false) { onClick?.invoke() },
    ) {
        Canvas(
            modifier = Modifier
                .padding(top = 3.dp)
                .size(16.dp)
                .background(
                    color = radioBackgroundColor,
                    shape = CircleShape
                )
                .padding(4.dp)
                .align(alignment)
        ) {
            drawCircle(color = SDGColor.Neutral0)
        }
        Spacer(modifier = Modifier.width(8.dp))
        IOText(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f),
            text = text,
            textColor = textColor,
            fontSize = 16.sp,
            onTextLayout = { textLayoutResult = it }
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
private fun IORadioPreview() {
    var isChecked by remember {
        mutableStateOf(false)
    }
    Scaffold(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .wrapContentHeight(),
        ) {
            IORadio(
                isChecked = isChecked,
                text = "TEST \nCASE \nCASDASD",
                onClick = {
                    isChecked = !isChecked
                }
            )
        }
    }

}

private fun textCenterAlignment(
    textLayoutResult: TextLayoutResult?,
    lineCount: Int,
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
