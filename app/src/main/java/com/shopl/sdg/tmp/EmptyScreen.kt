package com.shopl.sdg.tmp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor

@Composable
fun EmptyScreen(onClick: () -> Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SDGColor.Red300)
            .clickable { onClick() }
    )
}