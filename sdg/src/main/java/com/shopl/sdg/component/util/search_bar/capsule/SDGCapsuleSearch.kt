package com.shopl.sdg.component.util.search_bar.capsule

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.search_bar.capsule.SDGCapsuleSearch
import com.shopl.sdg.component.search_bar.capsule.SDGCapsuleSearchType
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_resource.R as SDGResource

/**
 * [RowScope]에서 사용되는 [SDGCapsuleSearch]
 * weight 값 지정 가능
 */
@JvmOverloads
@Composable
fun RowScope.SDGCapsuleSearch(
    weight: Float,
    type: SDGCapsuleSearchType,
    input: String,
    hint: String,
    enabled: Boolean,
    onInputChange: (String) -> Unit,
    onDeleteClick: () -> Unit,
    marginValues: PaddingValues = PaddingValues(),
    useStartRequester: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Box(modifier = Modifier.weight(weight = weight)) {
        SDGCapsuleSearch(
            type = type,
            input = input,
            hint = hint,
            enabled = enabled,
            onInputChange = onInputChange,
            onDeleteClick = onDeleteClick,
            marginValues = marginValues,
            useStartRequester = useStartRequester,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )
    }
}

@Preview
@Composable
private fun PreviewSDGCapsuleSearch() {
    Row(modifier = Modifier.background(SDGColor.Neutral100)) {
        SDGImage(
            resId = SDGResource.drawable.ic_navi_back_android,
            color = SDGColor.Neutral700
        )
        SDGCapsuleSearch(
            weight = 1f,
            type = SDGCapsuleSearchType.Solid,
            input = "Input",
            hint = "Hint",
            enabled = true,
            onInputChange = {},
            onDeleteClick = {},
            marginValues = PaddingValues(),
            useStartRequester = true,
            keyboardOptions = KeyboardOptions.Default,
            keyboardActions = KeyboardActions.Default,
        )
        SDGImage(
            resId = SDGResource.drawable.ic_navi_close,
            color = SDGColor.Neutral700
        )
    }
}