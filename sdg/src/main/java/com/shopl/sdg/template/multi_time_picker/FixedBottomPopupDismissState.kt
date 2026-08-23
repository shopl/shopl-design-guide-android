package com.shopl.sdg.template.multi_time_picker

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * [FixedBottomPopup]의 닫힘 애니메이션이 완료된 뒤 실행할 액션을 관리합니다.
 */
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
