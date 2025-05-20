package com.shopl.sdg.component.control.checkbox

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_resource.R

@Composable
fun SDGCheckBox(
    isChecked: Boolean,
    clickPadding: PaddingValues = PaddingValues(),
    onClick: (() -> Unit)? = null,
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (isChecked)
            SDGColor.Primary300
        else
            SDGColor.Neutral200,
        animationSpec = tween(
            durationMillis = 300,
            easing = FastOutSlowInEasing
        )
    )

    Box(
        modifier = Modifier
            .wrapContentSize(align = Alignment.Center)
            .padding(clickPadding)
            .then(
                if (onClick != null) {
                    Modifier.clickable(
                        hasRipple = false,
                        onClick = { onClick() }
                    )
                } else {
                    Modifier
                }
            )
            .clip(shape = RoundedCornerShape(4.dp))
            .background(color = backgroundColor)
    ) {

        Image(
            modifier = Modifier
                .padding(1.dp)
                .wrapContentSize(align = Alignment.Center),
            painter = painterResource(
                id = R.drawable.ic_common_check_s,
            ),
            colorFilter = ColorFilter.tint(SDGColor.Neutral0),
            contentDescription = ""
        )

    }
}
