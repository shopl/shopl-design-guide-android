package com.shopl.sdg.component.empty_icon

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_common.ui.components.SDGText
import com.shopl.sdg_resource.R

@Composable
fun SDGBasicEmpty(
    @DrawableRes
    iconResId: Int,
    @StringRes
    descResId: Int,
    marginTop: Dp = 60.dp
) {
    SDGBasicEmpty(
        iconResId = iconResId,
        descriptSDGn = stringResource(descResId),
        marginTop = marginTop
    )
}

@Composable
fun SDGBasicEmpty(
    @DrawableRes
    iconResId: Int,
    descriptSDGn: String,
    marginTop: Dp = 60.dp
) {
    Column(
        modifier = Modifier
            .padding(top = marginTop)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = spacedBy(12.dp)
    ) {
        SDGImage(
            resId = iconResId,
            color = null
        )

        SDGText(
            text = descriptSDGn,
            textColor = SDGColor.Neutral300,
            typography = SDGTypography.Body2R,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PrevEmpty() {
    Surface(
        Modifier
            .fillMaxSize()
            .background(SDGColor.Neutral350)
    ) {
        Column() {
            SDGBasicEmpty(iconResId = R.drawable.empty_member, descResId = R.string.no_registered_members)
        }
    }
}