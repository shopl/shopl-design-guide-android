package com.shopl.sdg.component.document

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.IOText
import com.shopl.sdg_resource.R

@Composable
fun SDGSimpleDocument(
    modifier: Modifier = Modifier.fillMaxWidth(),
    documentName: String,
    documentExtension: String,
    documentSize: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .clickable(false) { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_clip),
            contentDescription = null,
            colorFilter = ColorFilter.tint(SDGColor.Neutral400)
        )
        Spacer(modifier = Modifier.width(4.dp))
        IOText(
            modifier = Modifier.weight(1F, false),
            text = documentName,
            textColor = SDGColor.Neutral600,
            fontSize = 14.sp,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
        Row(
            modifier = Modifier.wrapContentWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IOText(
                text = ".$documentExtension",
                textColor = SDGColor.Neutral600,
                fontSize = 14.sp,
                maxLines = 1,
            )
            IOText(
                text = "($documentSize)",
                textColor = SDGColor.Neutral400,
                fontSize = 14.sp,
                maxLines = 1,
            )
        }
    }
}