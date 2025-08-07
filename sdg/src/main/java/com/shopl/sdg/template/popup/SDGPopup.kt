package com.shopl.sdg.template.popup

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.IOText
import com.shopl.sdg_common.ui.components.IOTypeface
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_common.util.textCenterAlignment
import com.shopl.sdg_resource.R

@Composable
fun SDGConfirmPopup(
    modifier: Modifier = Modifier,
    cancelLabel: String = stringResource(id = R.string.dialog_common_btn_cancel),
    confirmLabel: String = stringResource(id = R.string.dialog_common_btn_ok),
    confirmLabelColor: Color = SDGColor.Neutral700,
    onClickCancel: (() -> Unit)? = null,
    onClickConfirm: () -> Unit,
    onDismissRequest: (() -> Unit)? = null,
    isSingleButton: Boolean = false,
    title: String,
    description: String? = null
) {
    SDGConfirmPopup(
        modifier = modifier,
        cancelLabel = cancelLabel,
        confirmLabel = confirmLabel,
        confirmLabelColor = confirmLabelColor,
        onClickCancel = onClickCancel,
        onClickConfirm = onClickConfirm,
        onDismissRequest = onDismissRequest,
        title = AnnotatedString(text = title),
        description = description,
        isSingleButton = isSingleButton
    )
}

@Composable
fun SDGConfirmPopup(
    modifier: Modifier = Modifier,
    cancelLabel: String = stringResource(id = R.string.dialog_common_btn_cancel),
    confirmLabel: String = stringResource(id = R.string.dialog_common_btn_ok),
    confirmLabelColor: Color = SDGColor.Neutral700,
    onClickCancel: (() -> Unit)? = null,
    onClickConfirm: () -> Unit,
    onDismissRequest: (() -> Unit)? = null,
    title: AnnotatedString,
    description: String? = null,
    isSingleButton: Boolean = false,
) {
    SDGPopup(
        modifier = modifier,
        singleButton = isSingleButton,
        cancelLabel = cancelLabel,
        confirmLabel = confirmLabel,
        confirmLabelColor = confirmLabelColor,
        onClickCancel = onClickCancel,
        onClickConfirm = onClickConfirm,
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            IOText(
                text = title,
                textColor = SDGColor.Neutral700,
                fontSize = 18.sp,
                typeface = IOTypeface.SEMI_BOLD
            )
            description?.let {
                IOText(
                    modifier = Modifier.padding(top = 16.dp),
                    text = it,
                    textColor = SDGColor.Neutral600,
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Composable
fun SDGInfoPopup(
    confirmLabel: String = stringResource(id = R.string.dialog_common_btn_ok),
    onClickConfirm: () -> Unit,
    title: String,
    description: String? = null
) {
    SDGInfoPopup(
        confirmLabel = confirmLabel,
        onClickConfirm = onClickConfirm,
        title = AnnotatedString(text = title),
        description = description?.let { AnnotatedString(text = it) }
    )
}

@Composable
fun SDGInfoPopup(
    confirmLabel: String = stringResource(id = R.string.dialog_common_btn_ok),
    onClickConfirm: () -> Unit,
    title: AnnotatedString,
    description: String? = null
) {
    SDGInfoPopup(
        confirmLabel = confirmLabel,
        onClickConfirm = onClickConfirm,
        title = title,
        description = description?.let { AnnotatedString(text = it) }
    )
}

@Composable
fun SDGInfoPopup(
    confirmLabel: String = stringResource(id = R.string.dialog_common_btn_ok),
    onClickConfirm: () -> Unit,
    title: String,
    description: AnnotatedString
) {
    SDGInfoPopup(
        confirmLabel = confirmLabel,
        onClickConfirm = onClickConfirm,
        title = AnnotatedString(text = title),
        description = description
    )
}

@Composable
fun SDGInfoPopup(
    confirmLabel: String = stringResource(id = R.string.dialog_common_btn_ok),
    onClickConfirm: () -> Unit,
    title: AnnotatedString,
    description: AnnotatedString? = null
) {
    SDGPopup(
        singleButton = true,
        confirmLabel = confirmLabel,
        onClickConfirm = onClickConfirm
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            IOText(
                text = title,
                textColor = SDGColor.Neutral700,
                fontSize = 18.sp,
                typeface = IOTypeface.SEMI_BOLD
            )
            description?.let {
                IOText(
                    modifier = Modifier.padding(top = 16.dp),
                    text = it,
                    textColor = SDGColor.Neutral600,
                    fontSize = 16.sp,
                )
            }
        }
    }
}

@Composable
fun SDGIconPopup(
    @DrawableRes resId: Int,
    confirmLabel: String = stringResource(id = R.string.dialog_common_btn_ok),
    onClickConfirm: () -> Unit,
    title: String,
    description: String? = null
) {
    SDGPopup(
        singleButton = true,
        confirmLabel = confirmLabel,
        onClickConfirm = onClickConfirm
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SDGImage(
                modifier = Modifier
                    .size(75.dp),
                resId = resId,
                color = null
            )
            SDGText(
                modifier = Modifier.padding(top = 20.dp),
                text = title,
                typography = SDGTypography.Title2SB,
                textColor = SDGColor.Neutral700
            )
            description?.let {
                SDGText(
                    modifier = Modifier.padding(top = 12.dp),
                    text = it,
                    typography = SDGTypography.Body1R,
                    textColor = SDGColor.Neutral600,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@SuppressLint("NewApi")
@Composable
fun SDGPopup(
    modifier: Modifier = Modifier,
    singleButton: Boolean,
    isConfirmEnable: Boolean = true,
    confirmLabelColor: Color = SDGColor.Neutral700,
    cancelLabel: String = stringResource(id = R.string.dialog_common_btn_cancel),
    confirmLabel: String = stringResource(id = R.string.dialog_common_btn_ok),
    onDismissRequest: (() -> Unit)? = null,
    onClickCancel: (() -> Unit)? = null,
    onClickConfirm: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(horizontal = 28.dp, vertical = 32.dp),
    content: @Composable () -> Unit,
) {
    Dialog(
        onDismissRequest = {
            onDismissRequest?.let {
                it.invoke()
            } ?: run {
                if (!singleButton) {
                    onClickCancel?.invoke()
                } else {
                    onClickConfirm.invoke()
                }
            }
        },
        DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        (LocalView.current.parent as DialogWindowProvider).window.setDimAmount(0.4f)

        Column(
            modifier = modifier
                .padding(horizontal = 20.dp)
                .heightIn(max = (LocalConfiguration.current.screenHeightDp - 120).dp)
                .widthIn(max = (LocalConfiguration.current.screenWidthDp - 40).dp)
                .background(
                    color = SDGColor.Neutral0,
                    shape = RoundedCornerShape(20.dp)
                )
        ) {
            Box(
                modifier = Modifier
                    .weight(1f, false)
                    .padding(contentPadding)
            ) {
                content.invoke()
            }
            SDGPopupBottomButton(
                singleButton = singleButton,
                cancelLabel = cancelLabel,
                confirmLabel = confirmLabel,
                onClickCancel = onClickCancel,
                onClickConfirm = onClickConfirm,
                isConfirmEnable = isConfirmEnable,
                confirmLabelColor = confirmLabelColor
            )
        }
    }
}

@Composable
fun ColumnScope.SDGPopupBottomButton(
    singleButton: Boolean,
    cancelLabel: String = stringResource(id = R.string.dialog_common_btn_cancel),
    confirmLabel: String = stringResource(id = R.string.dialog_common_btn_ok),
    onClickCancel: (() -> Unit)? = null,
    onClickConfirm: () -> Unit,
    isConfirmEnable: Boolean = true,
    confirmLabelColor: Color = SDGColor.Neutral700,
    isBottomDialog: Boolean = false
) {
    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(
                color = SDGColor.Neutral200
            )
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(if (isBottomDialog) 58.dp else 50.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (!singleButton) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(bottomStart = 20.dp))
                    .weight(1F)
                    .fillMaxHeight()
                    .clickable(hasRipple = true, rippleColor = SDGColor.Neutral400) {
                        onClickCancel?.invoke()
                    },
            ) {
                SDGText(
                    modifier = Modifier.align(Alignment.Center),
                    text = cancelLabel,
                    textColor = SDGColor.Neutral700,
                    typography = SDGTypography.Body1R,
                    textAlign = TextAlign.Center
                )
            }
            Spacer(
                modifier = Modifier
                    .then(
                        if (isBottomDialog) {
                            Modifier.height(18.dp)
                        } else {
                            Modifier.fillMaxHeight()
                        }
                    )
                    .width(1.dp)
                    .background(
                        color = SDGColor.Neutral200
                    )
            )
        }
        Box(
            modifier = Modifier
                .then(
                    if (singleButton) {
                        Modifier.clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                    } else {
                        Modifier.clip(RoundedCornerShape(bottomEnd = 20.dp))
                    }
                )
                .weight(1F)
                .fillMaxHeight()
                .then(
                    if (isConfirmEnable) {
                        Modifier.clickable(hasRipple = true, rippleColor = SDGColor.Neutral400) {
                            onClickConfirm.invoke()
                        }
                    } else {
                        Modifier
                    }
                ),
        ) {
            SDGText(
                modifier = Modifier.align(Alignment.Center),
                text = confirmLabel,
                textColor = if (isConfirmEnable) confirmLabelColor else confirmLabelColor.copy(0.3F),
                typography = SDGTypography.Body1SB,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
fun SDGPopupBodyBullet(
    modifier: Modifier = Modifier,
    text: String,
    fontSize: TextUnit = 14.sp,
    typeface: IOTypeface = IOTypeface.REGULAR,
) {
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val alignment = remember(textLayoutResult) {
        textCenterAlignment(textLayoutResult, lineCount = 0)
    }

    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        Canvas(
            modifier = Modifier
                .size(4.dp)
                .align(alignment)
        ) {
            drawCircle(color = SDGColor.Neutral600)
        }
        Spacer(modifier = Modifier.width(5.dp))
        SDGText(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f),
            text = text,
            textColor = SDGColor.Neutral600,
            typography = SDGTypography.Body1R,
            onTextLayout = { textLayoutResult = it }
        )
    }
}

@Composable
fun SDGPopupBodyBullet(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    fontSize: TextUnit = 14.sp,
    typeface: IOTypeface = IOTypeface.REGULAR,
) {
    var textLayoutResult by remember { mutableStateOf<TextLayoutResult?>(null) }
    val alignment = remember(textLayoutResult) {
        textCenterAlignment(textLayoutResult, lineCount = 0)
    }

    Row(
        modifier = modifier.fillMaxWidth(),
    ) {
        Canvas(
            modifier = Modifier
                .size(4.dp)
                .align(alignment)
        ) {
            drawCircle(color = SDGColor.Neutral600)
        }
        Spacer(modifier = Modifier.width(5.dp))
        IOText(
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(1f),
            text = text,
            textColor = SDGColor.Neutral600,
            fontSize = fontSize,
            typeface = typeface,
            onTextLayout = { textLayoutResult = it }
        )
    }
}

@Composable
@Preview
private fun PrevTest() {
    Surface(Modifier.fillMaxSize()) {
        SDGConfirmPopup(
            onClickConfirm = { },
            onClickCancel = { },
            title = "Title",
            description = "Description"
        )
    }
}

@Composable
@Preview
private fun PrevTest2() {
    Surface(Modifier.fillMaxSize()) {
        SDGInfoPopup(
            onClickConfirm = { },
            title = "Title",
            description = "Description"
        )
    }
}

@Composable
@Preview
private fun SDGPopupBottomButtonPreview1() {
    Column(
        modifier = Modifier.background(
            color = SDGColor.Neutral0
        ),
    ) {
        SDGPopupBottomButton(
            singleButton = false,
            onClickConfirm = { },
            onClickCancel = { }
        )

    }
}

@Composable
@Preview
private fun SDGPopupBottomButtonPreview2() {
    Column(
        modifier = Modifier.background(
            color = SDGColor.Neutral0
        ),
    ) {
        SDGPopupBottomButton(
            singleButton = false,
            onClickConfirm = { },
            onClickCancel = { },
            isBottomDialog = true
        )
    }
}

@Composable
@Preview
private fun PreviewSDGIconPopup() {
    Surface(Modifier.fillMaxSize()) {
        SDGIconPopup(
            resId = R.drawable.popup_ic_warning,
            onClickConfirm = {},
            title = "기록을 남길 수 없습니다.",
            description = "정시 출근 인정 범위가 적용되어 10:00 에 출근한 것으로 처리되었습니다.\n10:00 이후에 다시 시도해주세요."
        )
    }
}