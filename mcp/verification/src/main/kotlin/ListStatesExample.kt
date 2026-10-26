package com.shopl.sdg.mcp.examples

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import com.shopl.sdg.template.empty.SDGEmpty

/** 조회 결과의 상태입니다. 조회·재시도 정책은 호출 화면에서 결정합니다. */
enum class ListExampleState {
    LOADING,
    CONTENT,
    EMPTY,
    ERROR,
}

/** 로딩·목록·오류 콘텐츠는 앱의 기존 UI를 연결합니다. SDG는 빈 상태 표시를 담당합니다. */
@Composable
fun ListStatesExample(
    state: ListExampleState,
    @DrawableRes emptyIllustration: Int,
    emptyTitle: String,
    loading: @Composable () -> Unit,
    content: @Composable () -> Unit,
    error: @Composable () -> Unit,
) {
    when (state) {
        ListExampleState.LOADING -> loading()
        ListExampleState.CONTENT -> content()
        ListExampleState.EMPTY -> SDGEmpty(illustrationResId = emptyIllustration, title = emptyTitle)
        ListExampleState.ERROR -> error()
    }
}
