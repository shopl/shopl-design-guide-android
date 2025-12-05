package com.shopl.sdg.tmp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

@Composable
fun HomeScreen(
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(SDGColor.Primary300),
        contentAlignment = Alignment.Center
    ) {
        SDGText(
            text = "홈 화면",
            typography = SDGTypography.Title2SB,
            textColor = SDGColor.Neutral900,
            modifier = Modifier.clickable { onClick() }
        )
    }
}
