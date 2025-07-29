package com.shopl.sdg.component.check_option

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg_common.ext.clickable
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.ui.components.SDGImage
import com.shopl.sdg_resource.R

@Composable
fun SDGCheck(
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
            .clip(shape = CircleShape)
            .background(backgroundColor),
    ) {

        SDGImage(
            modifier = Modifier
                .wrapContentSize(align = Alignment.Center),
            resId = R.drawable.ic_common_check_s,
            color = SDGColor.Neutral0,
        )
    }
}

@Preview
@Composable
private fun PreviewSDGCheck() {
    var test by remember {
        mutableStateOf(false)
    }
    SDGCheck(clickPadding = PaddingValues(20.dp), isChecked = test) {
        test = !test
    }
}