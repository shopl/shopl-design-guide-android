package com.shopl.sdg.template.multi_time_picker

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import com.shopl.sdg.template.popup.SDGPopupBottomButton
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_resource.R

/**
 * 스와이프 제스처로 닫히지 않는 Dialog 기반 하단 고정 팝업입니다.
 *
 * @param singleButton 단일 버튼 여부
 * @param onClickConfirm 확인 버튼 클릭 시 호출
 * @param contentPadding 본문 콘텐츠 여백
 */
@Composable
internal fun FixedBottomPopup(
    singleButton: Boolean,
    onClickConfirm: () -> Unit,
    modifier: Modifier = Modifier,
    containerColor: Color = SDGColor.Neutral0,
    contentColor: Color = SDGColor.Neutral0,
    cancelLabel: String = stringResource(id = R.string.dialog_common_btn_cancel),
    confirmLabel: String = stringResource(id = R.string.dialog_common_btn_ok),
    onClickCancel: (() -> Unit)? = null,
    isConfirmEnable: Boolean = true,
    confirmLabelColor: Color = SDGColor.Neutral700,
    contentPadding: PaddingValues = PaddingValues(
        start = SDGSpacing.Spacing24,
        top = SDGSpacing.Spacing24,
        end = SDGSpacing.Spacing24,
        bottom = SDGSpacing.Spacing28,
    ),
    content: @Composable () -> Unit,
) {
    val dismissState = rememberFixedBottomPopupDismissState()
    dismissState.updateDefaultAction(onClickCancel ?: onClickConfirm)

    FixedBottomPopupContent(
        state = dismissState,
        singleButton = singleButton,
        modifier = modifier,
        containerColor = containerColor,
        contentColor = contentColor,
        cancelLabel = cancelLabel,
        confirmLabel = confirmLabel,
        onClickCancel = onClickCancel,
        onClickConfirm = onClickConfirm,
        isConfirmEnable = isConfirmEnable,
        confirmLabelColor = confirmLabelColor,
        contentPadding = contentPadding,
        content = content,
    )
}

@Composable
private fun FixedBottomPopupContent(
    state: FixedBottomPopupDismissState,
    singleButton: Boolean,
    modifier: Modifier,
    containerColor: Color,
    contentColor: Color,
    cancelLabel: String,
    confirmLabel: String,
    onClickCancel: (() -> Unit)?,
    onClickConfirm: () -> Unit,
    isConfirmEnable: Boolean,
    confirmLabelColor: Color,
    contentPadding: PaddingValues,
    content: @Composable () -> Unit,
) {
    LaunchedEffect(state.transitionState.currentState, state.transitionState.isIdle) {
        if (!state.transitionState.targetState &&
            state.transitionState.isIdle &&
            !state.transitionState.currentState
        ) {
            state.consumePendingAction()
        }
    }

    Dialog(
        onDismissRequest = { state.requestDismiss() },
        properties = DialogProperties(
            dismissOnClickOutside = false,
            dismissOnBackPress = true,
            usePlatformDefaultWidth = false,
            decorFitsSystemWindows = false,
        )
    ) {
        val window = (LocalView.current.parent as DialogWindowProvider).window
        LaunchedEffect(state.transitionState.targetState) {
            window.setDimAmount(if (state.transitionState.targetState) 0.4f else 0f)
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Spacer(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable { state.requestDismiss() }
            )

            AnimatedVisibility(
                visibleState = state.transitionState,
                enter = slideInVertically { it } + fadeIn(),
                exit = slideOutVertically { it } + fadeOut(),
            ) {
                Surface(
                    modifier = modifier.fillMaxWidth(),
                    color = containerColor,
                    contentColor = contentColor,
                    shape = RoundedCornerShape(
                        topStart = SDGCornerRadius.Radius20,
                        topEnd = SDGCornerRadius.Radius20,
                    ),
                    tonalElevation = 0.dp,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .navigationBarsPadding()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(contentPadding),
                        ) {
                            content()
                        }

                        SDGPopupBottomButton(
                            singleButton = singleButton,
                            cancelLabel = cancelLabel,
                            confirmLabel = confirmLabel,
                            onClickCancel = onClickCancel?.let { cancel ->
                                { state.requestDismiss(cancel) }
                            },
                            onClickConfirm = { state.requestDismiss(onClickConfirm) },
                            isConfirmEnable = isConfirmEnable,
                            confirmLabelColor = confirmLabelColor,
                            isBottomDialog = true,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun rememberFixedBottomPopupDismissState(): FixedBottomPopupDismissState =
    remember { FixedBottomPopupDismissState() }

@Stable
internal class FixedBottomPopupDismissState {
    val transitionState = MutableTransitionState(false).apply { targetState = true }

    private var defaultAction: (() -> Unit)? = null
    private var pendingAction: (() -> Unit)? by mutableStateOf(null)

    fun updateDefaultAction(action: () -> Unit) {
        defaultAction = action
    }

    fun requestDismiss(action: (() -> Unit)? = null) {
        if (!transitionState.targetState) return

        pendingAction = action ?: defaultAction
        transitionState.targetState = false
    }

    fun consumePendingAction() {
        pendingAction?.invoke()
        pendingAction = null
    }
}
