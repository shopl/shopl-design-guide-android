package com.shopl.sdg.template.navigation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.search_bar.capsule.SDGCapsuleSearchType
import com.shopl.sdg.component.util.search_bar.capsule.SDGCapsuleSearch
import com.shopl.sdg.template.navigation.search.preview.SDGSearchNaviPreviewData
import com.shopl.sdg.template.navigation.search.preview.SDGSearchNaviPreviewParameterProvider
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.ui.components.SDGImage

/**
 * SDG - Navigation - Search Navi
 *
 * 화면의 상단에 위치하며, 검색바와 조합된 템플릿
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19821-30930&t=6UHqf2CQvDUAo0we-4">Figma</a>
 */
@Composable
fun SDGSearchNavi(
    type: SDGSearchNaviType,
    input: String,
    hint: String,
    backgroundColor: Color,
    onInputChange: (String) -> Unit,
    onDeleteClick: () -> Unit,
    useStartRequester: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    Row(
        modifier = Modifier
            .background(backgroundColor)
            .height(48.dp)
            .fillMaxWidth()
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (type is SDGSearchNaviType.Back) {
            SearchNaviIconButton(type.icon)
        }

        SDGCapsuleSearch(
            weight = 1f,
            type = SDGCapsuleSearchType.Solid,
            input = input,
            hint = hint,
            enabled = true,
            onInputChange = onInputChange,
            onDeleteClick = onDeleteClick,
            useStartRequester = useStartRequester,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
        )

        if (type is SDGSearchNaviType.Full) {
            SearchNaviIconButton(type.icon)
        }
    }
}

@Composable
private fun SearchNaviIconButton(icon: SDGSearchNaviIcon) {
    SDGImage(
        modifier = Modifier
            .size(40.dp)
            .clickable(false) { icon.onClick() },
        resId = icon.iconResId,
        color = icon.iconColor,
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGSearchNavi(
    @PreviewParameter(SDGSearchNaviPreviewParameterProvider::class)
    previewData: SDGSearchNaviPreviewData
) {
    SDGSearchNavi(
        type = previewData.type,
        input = previewData.input,
        hint = previewData.hint,
        backgroundColor = previewData.backgroundColor,
        onInputChange = {},
        onDeleteClick = {},
        useStartRequester = false
    )
}
