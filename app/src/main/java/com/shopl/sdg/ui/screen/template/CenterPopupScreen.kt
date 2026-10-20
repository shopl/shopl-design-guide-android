package com.shopl.sdg.ui.screen.template

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.button.ghost.SDGGhostButton
import com.shopl.sdg.component.button.ghost.SDGGhostButtonSize
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInputState
import com.shopl.sdg.scene.TemplateScene
import com.shopl.sdg.template.popup.center.SDGCenterPopupButtonOption
import com.shopl.sdg.template.popup.center.input.SDGInputCenterPopup
import com.shopl.sdg.template.popup.center.input.SDGInputCenterPopupShowInputLabel
import com.shopl.sdg.ui.base.SDGSampleBaseScaffold
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.ext.withColor
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText

private const val REJECTION_REASON_MAX_LENGTH = 200

@Composable
internal fun CenterPopupScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    var currentSample by remember { mutableStateOf<InputCenterPopupSample?>(null) }
    var inputContent by remember { mutableStateOf("") }
    var result by remember { mutableStateOf("팝업을 열어 내용을 입력해 보세요.") }
    val onConfirm = {
        result = "확인: $inputContent"
        currentSample = null
    }
    val onCancel = {
        result = "취소"
        currentSample = null
    }

    SDGSampleBaseScaffold(
        name = TemplateScene.Popup.CenterPopup.displayLabel,
        description = "Input | 입력이 필요한",
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
        bodyContent = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(SDGSpacing.Spacing24),
                verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing12),
            ) {
                InputCenterPopupSample.entries.forEach { sample ->
                    SDGGhostButton(
                        size = SDGGhostButtonSize.Large,
                        label = sample.label,
                        labelColor = SDGColor.Neutral700,
                        isFillMaxWidth = true,
                        onClick = {
                            inputContent = when (sample) {
                                InputCenterPopupSample.Error -> "잘못된 입력"
                                InputCenterPopupSample.Disabled -> "입력된 내용"
                                else -> ""
                            }
                            currentSample = sample
                        },
                    )
                }
                SDGText(
                    text = result,
                    textColor = SDGColor.Neutral600,
                    typography = SDGTypography.Body1R,
                )
            }
        },
    )

    currentSample?.let { sample ->
        InputCenterPopupSampleDialog(
            sample = sample,
            inputContent = inputContent,
            onInputChange = { inputContent = it },
            onConfirm = onConfirm,
            onCancel = onCancel,
            onDismiss = {
                result = "닫기"
                currentSample = null
            },
        )
    }
}

@Composable
private fun InputCenterPopupSampleDialog(
    sample: InputCenterPopupSample,
    inputContent: String,
    onInputChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    onDismiss: () -> Unit,
) {
    val isRejectionReason = sample == InputCenterPopupSample.RejectionReason
    SDGInputCenterPopup(
        buttonOption = sample.buttonOption(inputContent, onConfirm, onCancel, onDismiss),
        title = if (isRejectionReason) "반려하시겠습니까?" else "팝업 타이틀",
        description = when (sample) {
            InputCenterPopupSample.OneButton -> "입력한 내용을 확인합니다."
            InputCenterPopupSample.RejectionReason -> "한 번 반려하면 되돌릴 수 없으며, 즉시 결재가 중단됩니다."
            InputCenterPopupSample.WithoutDescription,
            InputCenterPopupSample.WithoutInputLabel -> null
            else -> "Body text 하나의 기능을 설명하기 위한 기본 텍스트영역입니다."
        },
        showInputLabel = when (sample) {
            InputCenterPopupSample.WithoutInputLabel -> SDGInputCenterPopupShowInputLabel.False
            InputCenterPopupSample.RejectionReason -> SDGInputCenterPopupShowInputLabel.True(
                "사유*".withColor(SDGColor.Red300, "*"),
            )
            else -> SDGInputCenterPopupShowInputLabel.True("입력 라벨")
        },
        inputContent = inputContent,
        hint = if (isRejectionReason) "입력" else "힌트문구",
        state = sample.inputState(inputContent),
        onInputChange = onInputChange,
        inputMaxLength = if (isRejectionReason) REJECTION_REASON_MAX_LENGTH else Int.MAX_VALUE,
    )
}

private fun InputCenterPopupSample.inputState(inputContent: String): SDGFixedTextInputState = when (this) {
    InputCenterPopupSample.Error -> SDGFixedTextInputState.Error
    InputCenterPopupSample.Disabled -> SDGFixedTextInputState.Disabled
    else -> if (inputContent.isEmpty()) SDGFixedTextInputState.Default else SDGFixedTextInputState.Completed
}

private fun InputCenterPopupSample.buttonOption(
    inputContent: String,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
    onDismiss: () -> Unit,
): SDGCenterPopupButtonOption = if (this == InputCenterPopupSample.OneButton) {
    SDGCenterPopupButtonOption.OneOption(
        label = "확인",
        onClick = onConfirm,
        labelColor = SDGColor.Neutral700,
    )
} else {
    SDGCenterPopupButtonOption.TwoOption(
        cancelLabel = "취소",
        confirmLabel = if (this == InputCenterPopupSample.RejectionReason) "반려" else "확인",
        onClickCancel = onCancel,
        onClickConfirm = onConfirm,
        cancelLabelColor = SDGColor.Neutral700,
        confirmLabelColor = SDGColor.Neutral700,
        confirmEnabled = when (this) {
            InputCenterPopupSample.ConfirmDisabled -> false
            InputCenterPopupSample.RejectionReason -> inputContent.isNotEmpty()
            else -> true
        },
        onDismiss = onDismiss,
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewCenterPopupScreen() {
    ShoplDesignGuideTheme {
        CenterPopupScreen(onClickBack = {}, onClickMenu = {})
    }
}
