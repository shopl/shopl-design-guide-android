package com.shopl.sdg.template.popup.center

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.button.ghost.SDGGhostButton
import com.shopl.sdg.component.button.ghost.SDGGhostButtonSize
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing.Spacing4

@Composable
fun SDGOneOptionCenterPopupButtonUnit(
    label: String,
    onClick: () -> Unit,
    labelColor: Color = SDGColor.Neutral700,
    enabled: Boolean = true,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = SDGCornerRadius.Radius20, bottomEnd = SDGCornerRadius.Radius20))
            .background(SDGColor.Neutral0)
    ) {
        HorizontalDivider(color = SDGColor.Neutral200)

        SDGGhostButton(
            size = SDGGhostButtonSize.Large,
            label = label,
            labelColor = labelColor,
            onClick = onClick,
            enable = enabled,
            isFillMaxWidth = true,
            marginValues = PaddingValues(vertical = Spacing4)
        )
    }
}

@Preview
@Composable
private fun SDGOneOptionCenterPopupButtonUnitPreview() {
    SDGOneOptionCenterPopupButtonUnit(
        label = "Label",
        labelColor = SDGColor.Neutral700,
        onClick = {}
    )
}
