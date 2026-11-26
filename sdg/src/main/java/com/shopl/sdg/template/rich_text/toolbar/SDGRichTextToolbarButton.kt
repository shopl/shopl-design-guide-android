package com.shopl.sdg.template.rich_text.toolbar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.SDGCornerRadius
import com.shopl.sdg_common.ui.components.SDGImage

/**
 * Rich Text 툴바의 서식 버튼. 40×40px 터치 영역, State: Default / Selected / Disabled, Icon은 Instance Swap으로 교체.
 *
 * @version 2.4.61
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=30773-1786&m=dev">Figma</a>
 */
@Composable
fun SDGRichTextToolbarButton(
    action: SDGRichTextToolbarAction,
    state: SDGRichTextToolbarButtonState,
    onClick: () -> Unit,
) {
    val label = stringResource(action.description)
    val isSelected = state == SDGRichTextToolbarButtonState.Selected
    val shape = SDGCornerRadius.BoxRadius.Radius6
    Box(
        modifier = Modifier
            .size(40.dp)
            .semantics {
                contentDescription = label
                if (action.group != ToolbarGroup.History) selected = isSelected
            }
            .clickable(
                enabled = state != SDGRichTextToolbarButtonState.Disabled,
                role = Role.Button,
                onClick = onClick,
            ),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier.size(32.dp).then(
                if (isSelected) Modifier.background(SDGColor.Primary300_a10, shape)
                    .border(1.dp, SDGColor.Primary300_a10, shape)
                else Modifier
            ),
            contentAlignment = Alignment.Center,
        ) {
            SDGImage(
                resId = action.icon,
                color = state.iconColor,
                modifier = Modifier.size(24.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSDGRichTextToolbarButton() {
    Row {
        SDGRichTextToolbarButtonState.entries.forEach { state ->
            SDGRichTextToolbarButton(
                action = SDGRichTextToolbarAction.Bold,
                state = state,
                onClick = {},
            )
        }
    }
}
