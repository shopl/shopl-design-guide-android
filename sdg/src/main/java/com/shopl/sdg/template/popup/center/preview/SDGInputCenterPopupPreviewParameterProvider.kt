package com.shopl.sdg.template.popup.center.preview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.text_input.InputState

internal class SDGInputCenterPopupParameterProvider : PreviewParameterProvider<SDGInputCenterPopupPreviewData> {
    override val values: Sequence<SDGInputCenterPopupPreviewData> = sequenceOf(
        SDGInputCenterPopupPreviewData(
            title = "팝업 타이틀",
            description = null,
            confirmLabel = "확인",
            inputLabel = "입력 라벨",
            initialInput = "",
            hint = "힌트 문구",
            inputState = InputState.Enable
        ),
        SDGInputCenterPopupPreviewData(
            title = "팝업 타이틀",
            description = "이것은 입력 팝업에 대한 설명입니다.",
            confirmLabel = "확인",
            inputLabel = "입력 라벨",
            initialInput = "",
            hint = "힌트 문구",
            inputState = InputState.Enable
        ),
        SDGInputCenterPopupPreviewData(
            title = "팝업 타이틀",
            description = "이것은 입력 팝업에 대한 설명입니다.",
            confirmLabel = "확인",
            inputLabel = "입력 라벨",
            initialInput = "잘못된 입력",
            hint = "힌트 문구",
            inputState = InputState.Error("에러 메시지")
        ),
        SDGInputCenterPopupPreviewData(
            title = "팝업 타이틀",
            description = "이것은 입력 팝업에 대한 설명입니다.",
            confirmLabel = "확인",
            inputLabel = "입력 라벨",
            initialInput = "올바른 입력",
            hint = "힌트 문구",
            inputState = InputState.Disable
        ),
        SDGInputCenterPopupPreviewData(
            title = "팝업 타이틀",
            description = "이것은 입력 팝업에 대한 설명입니다.",
            confirmLabel = "확인",
            inputLabel = "입력 라벨",
            initialInput = "",
            hint = "힌트 문구",
            inputState = InputState.Enable,
            enabled = false
        )
    )
}

@Composable
internal fun SDGInputCenterPopupPreviewBody(data: SDGInputCenterPopupPreviewData) {
    var inputContent by remember { mutableStateOf(data.initialInput) }

    com.shopl.sdg.template.popup.center.input.SDGInputCenterPopup(
        title = data.title,
        description = data.description,
        confirmLabel = data.confirmLabel,
        onClickConfirm = {},
        inputLabel = data.inputLabel,
        inputContent = inputContent,
        hint = data.hint,
        inputState = data.inputState,
        onInputChange = { inputContent = it },
        enabled = data.enabled,
        titleAlignment = data.titleAlignment
    )
}

internal data class SDGInputCenterPopupPreviewData(
    val title: String?,
    val description: String?,
    val confirmLabel: String,
    val inputLabel: String,
    val initialInput: String,
    val hint: String,
    val inputState: InputState,
    val enabled: Boolean = true,
    val titleAlignment: TextAlign = TextAlign.Left,
)