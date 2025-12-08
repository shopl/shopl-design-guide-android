package com.shopl.sdg.util

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.EaseOutCirc
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically

internal const val TRANSITION_SPEC = "transitionSpec"
internal const val POP_TRANSITION_SPEC = "popTransitionSpec"
internal const val PREDICTIVE_POP_TRANSITION_SPEC = "predictivePopTransitionSpec"

fun nav3Transitions(
    enter: EnterTransition = baseEnterTransition(),
    exit: ExitTransition = baseExitTransition()
): Map<String, Any> = mapOf(
    TRANSITION_SPEC to enter,
    POP_TRANSITION_SPEC to exit
)

fun nav3Enter(enter: EnterTransition = baseEnterTransition()): Map<String, Any> =
    mapOf(TRANSITION_SPEC to enter)

fun nav3Exit(exit: ExitTransition = baseExitTransition()): Map<String, Any> =
    mapOf(POP_TRANSITION_SPEC to exit)

fun nav3PopupEnter(enter: EnterTransition = basePopupEnterTransition()): Map<String, Any> =
    mapOf(TRANSITION_SPEC to enter)

fun nav3PopupExit(exit: ExitTransition = basePopupExitTransition()): Map<String, Any> =
    mapOf(POP_TRANSITION_SPEC to exit)

private fun basePopupEnterTransition(): EnterTransition {
    return fadeIn(
        animationSpec = tween(
            200, easing = LinearEasing
        )
    ) + slideInVertically(
        animationSpec = tween(200, easing = EaseIn),
        initialOffsetY = { (it * 0.05).toInt() }
    )
}

private fun basePopupExitTransition(): ExitTransition {
    return fadeOut(
        animationSpec = tween(
            200, easing = LinearEasing
        )
    ) + slideOutVertically(
        animationSpec = tween(200, easing = EaseOut),
        targetOffsetY = { (it * 0.05).toInt() }
    )
}

private fun baseEnterTransition(): EnterTransition {
    return fadeIn(
        animationSpec = tween(
            300,
        )
    ) + slideInHorizontally(
        animationSpec = tween(300, easing = EaseOutCirc),
        initialOffsetX = { (it * 0.3).toInt() }
    )
}

private fun baseExitTransition(): ExitTransition {
    return fadeOut(
        animationSpec = tween(
            300,
        )
    ) + slideOutHorizontally(
        animationSpec = tween(200, easing = EaseOut),
        targetOffsetX = { (it * 0.3).toInt() }
    )
}