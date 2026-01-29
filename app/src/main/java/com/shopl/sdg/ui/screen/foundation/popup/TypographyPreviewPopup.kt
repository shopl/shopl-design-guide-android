package com.shopl.sdg.ui.screen.foundation.popup

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.R
import com.shopl.sdg.template.popup.center.SDGCenterPopup
import com.shopl.sdg.template.popup.center.SDGCenterPopupButtonOption
import com.shopl.sdg.ui.screen.foundation.model.TypographyUiModel
import com.shopl.sdg.ui.screen.foundation.model.toTypographyUiModel
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
internal fun TypographyPreviewPopup(
    uiModels: ImmutableList<TypographyUiModel>,
    onClickClose: () -> Unit
) {
    SDGCenterPopup(
        buttonOption = SDGCenterPopupButtonOption.OneOption(
            label = stringResource(R.string.close),
            onClick = onClickClose,
            labelColor = SDGColor.Neutral700,
        ),
        body = {
            TypographyPreviewPopupBody(uiModels)
        }
    )
}

@Composable
private fun TypographyPreviewPopupBody(
    uiModels: ImmutableList<TypographyUiModel>,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing20)
    ) {
        SDGText(
            text = stringResource(R.string.preview),
            textColor = SDGColor.Neutral350,
            typography = SDGTypography.Body2SB
        )
        uiModels.forEachIndexed { index, uiModel ->
            if (index != 0 && index % 2 == 0) {
                HorizontalDivider(color = SDGColor.Neutral200)
            }
            TypographyPreviewPopupContent(uiModel)
        }
    }
}

@Composable
private fun TypographyPreviewPopupContent(
    uiModel: TypographyUiModel
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing4)
    ) {
        SDGText(
            text = uiModel.styleLabel,
            textColor = SDGColor.Neutral700,
            typography = uiModel.typography
        )
        SDGText(
            text = "가나라다마바사",
            textColor = SDGColor.Neutral700,
            typography = uiModel.typography
        )
        SDGText(
            text = "1234567890 !@#$%^&*()",
            textColor = SDGColor.Neutral700,
            typography = uiModel.typography
        )
    }
}

@Preview
@Composable
private fun PreviewTypographyPreviewPopup() {
    TypographyPreviewPopup(
        uiModels = persistentListOf(
            SDGTypography.Title1SB.toTypographyUiModel(styleLabel = "Title 1 - SB"),
            SDGTypography.Title1R.toTypographyUiModel(styleLabel = "Title 1 - R"),
            SDGTypography.Title2SB.toTypographyUiModel(styleLabel = "Title 2 - SB"),
            SDGTypography.Title2R.toTypographyUiModel(styleLabel = "Title 2 - R"),
        ),
        onClickClose = {}
    )
}
