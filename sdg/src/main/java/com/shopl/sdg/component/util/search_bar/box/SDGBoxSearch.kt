package com.shopl.sdg.component.util.search_bar.box

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.search_bar.box.SDGBoxSearch
import com.shopl.sdg.component.search_bar.box.SDGBoxSearchType
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_resource.R as SDGResource

/**
 * [RowScope]에서 사용되는 [SDGBoxSearch]
 * weight 값 지정 가능
 */
@JvmOverloads
@Composable
fun RowScope.SDGBoxSearch(
    weight: Float,
    type: SDGBoxSearchType,
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
        SDGBoxSearch(
            type = type,
            input = input,
            hint = hint,
            enabled = enabled,
            onInputChange = onInputChange,
            onDeleteClick = onDeleteClick,
            marginValues = marginValues,
            useStartRequester = useStartRequester,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )
    }
}

@Preview
@Composable
private fun PreviewSDGBoxSearch() {
    Row {
        SDGImage(
            resId = SDGResource.drawable.ic_navi_back_android,
            color = SDGColor.Neutral700,
        )
        SDGBoxSearch(
            weight = 1f,
            type = SDGBoxSearchType.Solid(),
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
            color = SDGColor.Neutral700,
        )
    }
}
