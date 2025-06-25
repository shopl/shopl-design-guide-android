package com.shopl.sdg_common.util

import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.ViewTreeObserver
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalView
import com.shopl.sdg_common.enums.Keyboard

/**
 * 키보드의 열림/닫힘 상태를 관찰합니다.
 * 키보드 상태에 반응하는 UI 로직을 구현할 때 사용할 수 있습니다.
 *
 * 키패드가 화면 높이의 15% 이상 차지할 때 [Keyboard.Opened]로 판단합니다.
 *
 * @sample
 * ```
 * val keyboard by keyboardAsState()
 * if (keyboard == Keyboard.Opened) {
 *     // 키보드 열림에 대한 처리
 * }
 * ```
 */
@SuppressLint("NewApi")
@Composable
fun keyboardAsState(): State<Keyboard> {
    val keyboardState = remember { mutableStateOf(Keyboard.Closed) }
    val view = LocalView.current
    DisposableEffect(view) {
        val onGlobalListener = ViewTreeObserver.OnGlobalLayoutListener {
            val rect = Rect()
            view.getWindowVisibleDisplayFrame(rect)
            val screenHeight = view.rootView.height
            val keypadHeight = screenHeight - rect.bottom
            keyboardState.value = if (keypadHeight > screenHeight * 0.15) {
                Keyboard.Opened
            } else {
                Keyboard.Closed
            }
        }
        view.viewTreeObserver.addOnGlobalLayoutListener(onGlobalListener)

        onDispose {
            view.viewTreeObserver.removeOnGlobalLayoutListener(onGlobalListener)
        }
    }

    return keyboardState
}