package com.shopl.sdg.component.tab

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.IOText
import com.shopl.sdg_common.ui.components.IOTypeface

@Composable
fun SDGTextTab(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedItemIndex: Int,
    onClick: (index: Int) -> Unit,
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = modifier
            .horizontalScroll(scrollState)
    ) {
        items.forEachIndexed { index, s ->
            SDGTextTabItem(
                isSelected = selectedItemIndex == index,
                onClick = {
                    onClick(index)
                },
                text = s
            )
        }
    }
}

@Composable
private fun SDGTextTabItem(
    isSelected: Boolean,
    text: String,
    onClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .width(IntrinsicSize.Max)
            .padding(horizontal = 10.dp)
    ) {
        IOText(
            modifier = Modifier
                .height(28.dp)
                .clickable(hasRipple = false) {
                    onClick()
                },
            text = text,
            textColor = if (isSelected) {
                SDGColor.Neutral700
            } else {
                SDGColor.Neutral350
            },
            fontSize = 18.sp,
            typeface = IOTypeface.SEMI_BOLD,
            textAlign = TextAlign.Center
        )
        if (isSelected) {
            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth()
                    .background(color = SDGColor.Neutral700)
                    .padding(top = 2.dp)
            )
        } else {
            Spacer(
                modifier = Modifier
                    .height(2.dp)
                    .fillMaxWidth()
                    .padding(top = 2.dp)
            )
        }
    }
}