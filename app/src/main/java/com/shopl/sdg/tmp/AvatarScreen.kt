package com.shopl.sdg.tmp

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
fun AvatarScreen(onClick: () -> Boolean) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        SDGText(
            modifier = Modifier.clickable { onClick() },
            text = "Avatar Screen",
            typography = SDGTypography.Title2SB,
            textColor = SDGColor.Neutral900
        )
    }
}