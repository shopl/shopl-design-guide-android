package com.shopl.sdg.ui.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.attachment_element.SDGAttachmentElement
import com.shopl.sdg.component.attachment_element.SDGAttachmentElementState
import com.shopl.sdg.component.attachment_element.SDGAttachmentElementType
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.ui.base.SDGSampleBaseScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R as SDGResource

@Composable
internal fun AttachmentElementScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    SDGSampleBaseScaffold(
        name = ComponentScene.AttachmentElement.displayLabel,
        description = "사진, 문서, 동영상 등 첨부된 다양한 미디어 파일의 메타데이터와 포맷별 인디케이터를 시각화하는 컴포넌트",
        bodyContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SDGSpacing.Spacing16),
                verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing24),
            ) {
                AttachmentStateContent(SDGAttachmentElementState.Default)
                AttachmentStateContent(SDGAttachmentElementState.Uploading)
                AttachmentStateContent(SDGAttachmentElementState.Failed)
            }
        },
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
    )
}

@Composable
private fun AttachmentStateContent(
    state: SDGAttachmentElementState,
) {
    Column(verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing12)) {
        SDGText(
            text = state.title,
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body2SB,
        )

        SDGAttachmentElementType.entries.forEach { type ->
            SDGAttachmentElement(
                fileName = when (type) {
                    SDGAttachmentElementType.Photo -> "attachment.jpg"
                    SDGAttachmentElementType.Document -> "attachment.pdf"
                    SDGAttachmentElementType.Video -> "attachment.mp4"
                },
                fileSize = "4MB",
                state = state,
                type = type,
                imageModel = when (type) {
                    SDGAttachmentElementType.Document -> null
                    SDGAttachmentElementType.Photo -> SDGResource.drawable.empty_detail_payment
                    SDGAttachmentElementType.Video -> SDGResource.drawable.empty_member
                },
            )
        }
    }
}

@Preview
@Composable
private fun PreviewAttachmentElementScreen() {
    ShoplDesignGuideTheme {
        AttachmentElementScreen(
            onClickBack = {},
            onClickMenu = {},
        )
    }
}
