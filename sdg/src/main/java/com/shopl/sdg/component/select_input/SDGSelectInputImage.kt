package com.shopl.sdg.component.select_input

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable

/**
 * [SDGSelectInput]에 표시할 이미지 소스입니다.
 */
@Immutable
sealed interface SDGSelectInputImage {

    /**
     * Drawable 리소스 이미지입니다.
     */
    data class Resource(
        @DrawableRes val resId: Int,
    ) : SDGSelectInputImage

    /**
     * URL 이미지입니다.
     *
     * @param failureImageResId 이미지 로드 실패 시 표시할 Drawable 리소스
     */
    data class Url(
        val url: String,
        @DrawableRes val failureImageResId: Int? = null,
    ) : SDGSelectInputImage
}
