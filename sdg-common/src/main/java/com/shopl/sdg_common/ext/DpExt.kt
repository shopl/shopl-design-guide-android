package com.shopl.sdg_common.ext

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
fun dpToSp(dp: Dp) = with(LocalDensity.current) { dp.toSp() }

@Composable
fun dpToPx(dp: Dp) = with(LocalDensity.current) { dp.toPx() }

@Composable
fun pxToDp(px: Float) = with(LocalDensity.current) { px.toDp() }