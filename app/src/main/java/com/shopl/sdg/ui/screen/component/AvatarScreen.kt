package com.shopl.sdg.ui.screen.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.R
import com.shopl.sdg.component.avatar.SDGAvatar
import com.shopl.sdg.component.avatar.SDGAvatarSize
import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.template.checkbox_label.SDGCheckboxLabel
import com.shopl.sdg.template.checkbox_label.SDGCheckboxLabelSelectType
import com.shopl.sdg.template.checkbox_label.SDGCheckboxLabelState
import com.shopl.sdg.ui.base.SDGSampleBaseScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R as SDGResource

private const val AVATARS_PER_ROW = 3

/**
 * Avatar의 여섯 크기와 독립적인 표시 옵션을 확인하는 샘플입니다.
 * 재시도를 누르면 정상 표시로 돌아가며 프로필 클릭과 재시도 횟수를 각각 표시합니다.
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=27130-4525&m=dev">Figma</a>
 */
@Composable
internal fun AvatarScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    SDGSampleBaseScaffold(
        name = ComponentScene.Avatar.displayLabel,
        description = "유저의 얼굴 사진 또는 설정된 이미지를 보여주는 컴포넌트",
        bodyContent = { AvatarSampleContent() },
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
    )
}

/** 표시 옵션을 바꾸고 각 크기의 프로필 클릭·재시도 동작을 확인합니다. */
@Composable
private fun AvatarSampleContent() {
    var showFace by rememberSaveable { mutableStateOf(true) }
    var showOutline by rememberSaveable { mutableStateOf(false) }
    var showIc by rememberSaveable { mutableStateOf(false) }
    var showRetry by rememberSaveable { mutableStateOf(false) }
    var avatarClicks by rememberSaveable { mutableIntStateOf(0) }
    var retryClicks by rememberSaveable { mutableIntStateOf(0) }
    val sampleImage = "android.resource://${LocalContext.current.packageName}/${R.drawable.icon_sample}"
    val sizes = listOf(
        "XLarge" to SDGAvatarSize.XL,
        "Large" to SDGAvatarSize.L,
        "Medium" to SDGAvatarSize.M,
        "Small" to SDGAvatarSize.S,
        "XSmall" to SDGAvatarSize.XS,
        "XXSmall" to SDGAvatarSize.XXS,
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SDGSpacing.Spacing24),
        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing20),
    ) {
        AvatarSampleOption("Show Face (샘플 이미지)", showFace) { showFace = !showFace }
        AvatarSampleOption("Show Outline", showOutline) { showOutline = !showOutline }
        AvatarSampleOption("Show ic", showIc) { showIc = !showIc }
        AvatarSampleOption("Show Retry", showRetry) { showRetry = !showRetry }

        sizes.chunked(AVATARS_PER_ROW).forEach { rowSizes ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = SDGSpacing.Spacing12),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                rowSizes.forEach { (label, size) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing16),
                    ) {
                        SDGText(
                            text = label,
                            textColor = SDGColor.Neutral700,
                            typography = SDGTypography.Body3R,
                        )
                        SDGAvatar(
                            avatarSize = size,
                            userRegImg = sampleImage,
                            showFace = showFace,
                            showOutline = showOutline,
                            showIc = showIc,
                            iconResId = SDGResource.drawable.admin_badge,
                            showRetry = showRetry,
                            onClickRetry = {
                                retryClicks++
                                showRetry = false
                            },
                            onClickAvatar = { avatarClicks++ },
                        )
                    }
                }
            }
        }

        SDGText(
            text = "프로필 클릭: $avatarClicks / 재시도: $retryClicks",
            textColor = SDGColor.Neutral700,
            typography = SDGTypography.Body2R,
        )
    }
}

/** Figma의 Boolean 표시 속성을 샘플에서 직접 전환합니다. */
@Composable
private fun AvatarSampleOption(label: String, selected: Boolean, onClick: () -> Unit) {
    SDGCheckboxLabel(
        label = label,
        state = if (selected) SDGCheckboxLabelState.Selected else SDGCheckboxLabelState.Default,
        selectType = SDGCheckboxLabelSelectType.Normal,
        onClick = onClick,
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewAvatarScreen() {
    ShoplDesignGuideTheme {
        AvatarScreen(onClickBack = {}, onClickMenu = {})
    }
}
