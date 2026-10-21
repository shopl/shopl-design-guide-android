package com.shopl.sdg.template.popup.center.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.text_input.fixed.SDGFixedTextInputState
import com.shopl.sdg.template.popup.center.SDGCenterPopupButtonOption
import com.shopl.sdg.template.popup.center.input.SDGInputCenterPopupShowInputLabel
import com.shopl.sdg_common.ext.withColor
import com.shopl.sdg_common.foundation.SDGColor

internal class SDGInputCenterPopupPreviewParameterProvider : PreviewParameterProvider<SDGInputCenterPopupPreviewParameter> {
    override val values = sequenceOf(
        버튼_두개(),
        필수_사유_입력_전(),
        필수_사유_입력_완료(),
        본문_없음(),
        입력_라벨_없음(),
        버튼_한개(),
        입력_오류(),
        입력_비활성(),
        확인_비활성(),
        긴_문구(),
    )

    private fun 버튼_옵션(confirmEnabled: Boolean = true, confirmLabel: String = "확인") = SDGCenterPopupButtonOption.TwoOption(
        cancelLabel = "취소",
        confirmLabel = confirmLabel,
        onClickCancel = {},
        onClickConfirm = {},
        cancelLabelColor = SDGColor.Neutral700,
        confirmLabelColor = SDGColor.Neutral700,
        confirmEnabled = confirmEnabled,
    )

    private fun 버튼_두개() = SDGInputCenterPopupPreviewParameter(
        title = "팝업 타이틀",
        description = "Body text 하나의 기능을 설명하기 위한 기본 텍스트영역입니다.",
        buttonOption = 버튼_옵션(),
        showInputLabel = SDGInputCenterPopupShowInputLabel.True("label 또는 Text 문구"),
        initialInput = "",
        hint = "힌트문구",
        state = SDGFixedTextInputState.Default,
    )

    private fun 본문_없음() = 버튼_두개().copy(description = null)

    private fun 필수_사유_입력_전() = 버튼_두개().copy(
        title = "반려하시겠습니까?",
        description = "한 번 반려하면 되돌릴 수 없으며, 즉시 결재가 중단됩니다.",
        buttonOption = 버튼_옵션(confirmEnabled = false, confirmLabel = "반려"),
        showInputLabel = SDGInputCenterPopupShowInputLabel.True("사유*".withColor(SDGColor.Red300, "*")),
        hint = "입력",
    )

    private fun 필수_사유_입력_완료() = 필수_사유_입력_전().copy(
        buttonOption = 버튼_옵션(confirmLabel = "반려"),
        initialInput = "보고서 내용을 다시 확인해 주세요.",
        state = SDGFixedTextInputState.Completed,
    )

    private fun 입력_라벨_없음() = 본문_없음().copy(
        showInputLabel = SDGInputCenterPopupShowInputLabel.False,
    )

    private fun 버튼_한개() = 버튼_두개().copy(
        buttonOption = SDGCenterPopupButtonOption.OneOption(
            label = "확인",
            onClick = {},
            labelColor = SDGColor.Neutral700,
        ),
    )

    private fun 입력_오류() = 버튼_두개().copy(
        initialInput = "잘못된 입력",
        state = SDGFixedTextInputState.Error,
    )

    private fun 입력_비활성() = 버튼_두개().copy(
        initialInput = "입력된 내용",
        state = SDGFixedTextInputState.Disabled,
    )

    private fun 확인_비활성() = 버튼_두개().copy(
        buttonOption = 버튼_옵션(confirmEnabled = false),
    )

    private fun 긴_문구() = 버튼_두개().copy(
        title = "긴 팝업 타이틀은 줄바꿈하여 전체 내용을 표시합니다.",
        buttonOption = 버튼_옵션(confirmLabel = "긴 확인 버튼 라벨은 한 줄로 줄임말 처리합니다."),
        initialInput = "여러 줄로 입력한 내용은 입력 영역 안에서 스크롤할 수 있습니다.\n".repeat(8),
        state = SDGFixedTextInputState.Completed,
    )
}
