package com.shopl.sdg.template.rich_text.toolbar

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInput
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInputField
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInputState
import com.shopl.sdg.component.text_input.simple.SDGSimpleTextInputStyle
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

/**
 * Rich Text Toolbar의 Link 버튼을 눌렀을 때 툴바 위에 노출되는 URL 입력 영역.
 *
 * URL을 입력하면 확인 버튼이 활성화됩니다. 닫기는 입력을 적용하지 않고 [onDismiss]를 호출합니다.
 * @version 2.4.61
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=30799-2073&m=dev">Figma</a>
 */
@Composable
fun SDGRichTextLinkInput(
    url: String,
    onUrlChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    showTopDivider: Boolean = true,
) {
    val canConfirm = url.isNotBlank()
    Column(modifier = Modifier.fillMaxWidth().background(SDGColor.Neutral0)) {
        if (showTopDivider) HorizontalDivider(thickness = 1.dp, color = SDGColor.Neutral200)
        Column(
            modifier = Modifier.padding(SDGSpacing.Spacing12),
            verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing8),
        ) {
            SDGText(
                text = stringResource(R.string.rich_text_url_label),
                textColor = SDGColor.Neutral700,
                typography = SDGTypography.Body2R,
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.weight(1f).padding(end = SDGSpacing.Spacing8)) {
                    SDGSimpleTextInput(
                        text = url,
                        placeholder = "https://",
                        state = if (url.isEmpty()) SDGSimpleTextInputState.Default
                        else SDGSimpleTextInputState.Completed,
                        inputField = SDGSimpleTextInputField.White,
                        style = SDGSimpleTextInputStyle.Outlined,
                        onTextChange = onUrlChange,
                        maxLength = Int.MAX_VALUE,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri),
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing4)) {
                    LinkActionButton(
                        icon = R.drawable.ic_common_check_s,
                        description = stringResource(R.string.dialog_common_btn_ok),
                        background = if (canConfirm) SDGColor.Primary300 else SDGColor.Neutral50,
                        tint = if (canConfirm) SDGColor.Neutral0 else SDGColor.Neutral350,
                        enabled = canConfirm,
                        onClick = onConfirm,
                    )
                    LinkActionButton(
                        icon = R.drawable.ic_common_x,
                        description = stringResource(R.string.dialog_common_btn_cancel),
                        background = SDGColor.Neutral50,
                        tint = SDGColor.Neutral700,
                        enabled = true,
                        onClick = onDismiss,
                    )
                }
            }
        }
    }
}

@Composable
private fun LinkActionButton(
    @DrawableRes icon: Int,
    description: String,
    background: Color,
    tint: Color,
    enabled: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier.size(40.dp).clip(SDGCornerRadius.BoxRadius.Radius12)
            .background(background).clickable(enabled = enabled, role = Role.Button, onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        SDGImage(resId = icon, color = tint, modifier = Modifier.size(16.dp), contentDescription = description)
    }
}

@Preview(showBackground = true, widthDp = 375)
@Composable
private fun PreviewSDGRichTextLinkInput() {
    Column {
        listOf("", "https://www.shoplworks.com").forEach { url ->
            SDGRichTextLinkInput(
                url = url,
                onUrlChange = {},
                onConfirm = {},
                onDismiss = {},
            )
        }
    }
}
