package com.shopl.sdg.component.toggle

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.toggle.spec.SDGToggleSpec
import com.shopl.sdg.component.toggle.style.SDGToggleStyle

internal class SDGTogglePreviewParameterProvider :
    PreviewParameterProvider<SDGTogglePreviewParameter> {
    override val values: Sequence<SDGTogglePreviewParameter> = sequenceOf(
        off_PRIMARY_MEDIUM(),
        on_PRIMARY_MEDIUM(),
        disable_PRIMARY_MEDIUM(),
        off_NEUTRAL_MEDIUM(),
        on_NEUTRAL_MEDIUM(),
        disable_NEUTRAL_MEDIUM(),
        off_PRIMARY_SMALL(),
        on_PRIMARY_SMALL(),
        disable_PRIMARY_SMALL(),
        off_NEUTRAL_SMALL(),
        on_NEUTRAL_SMALL(),
        disable_NEUTRAL_SMALL(),
    )

    private fun off_PRIMARY_MEDIUM() = SDGTogglePreviewParameter(
        isOn = false,
        isEnabled = true,
        style = SDGToggleStyle.PRIMARY,
        spec = SDGToggleSpec.MEDIUM
    )

    private fun on_PRIMARY_MEDIUM() = SDGTogglePreviewParameter(
        isOn = true,
        isEnabled = true,
        style = SDGToggleStyle.PRIMARY,
        spec = SDGToggleSpec.MEDIUM
    )

    private fun disable_PRIMARY_MEDIUM() = SDGTogglePreviewParameter(
        isOn = false,
        isEnabled = false,
        style = SDGToggleStyle.PRIMARY,
        spec = SDGToggleSpec.MEDIUM
    )

    private fun off_NEUTRAL_MEDIUM() = SDGTogglePreviewParameter(
        isOn = false,
        isEnabled = true,
        style = SDGToggleStyle.NEUTRAL,
        spec = SDGToggleSpec.MEDIUM
    )

    private fun on_NEUTRAL_MEDIUM() = SDGTogglePreviewParameter(
        isOn = true,
        isEnabled = true,
        style = SDGToggleStyle.NEUTRAL,
        spec = SDGToggleSpec.MEDIUM
    )

    private fun disable_NEUTRAL_MEDIUM() = SDGTogglePreviewParameter(
        isOn = false,
        isEnabled = false,
        style = SDGToggleStyle.NEUTRAL,
        spec = SDGToggleSpec.MEDIUM
    )

    private fun off_PRIMARY_SMALL() = SDGTogglePreviewParameter(
        isOn = false,
        isEnabled = true,
        style = SDGToggleStyle.PRIMARY,
        spec = SDGToggleSpec.SMALL
    )

    private fun on_PRIMARY_SMALL() = SDGTogglePreviewParameter(
        isOn = true,
        isEnabled = true,
        style = SDGToggleStyle.PRIMARY,
        spec = SDGToggleSpec.SMALL
    )

    private fun disable_PRIMARY_SMALL() = SDGTogglePreviewParameter(
        isOn = false,
        isEnabled = false,
        style = SDGToggleStyle.PRIMARY,
        spec = SDGToggleSpec.SMALL
    )

    private fun off_NEUTRAL_SMALL() = SDGTogglePreviewParameter(
        isOn = false,
        isEnabled = true,
        style = SDGToggleStyle.NEUTRAL,
        spec = SDGToggleSpec.SMALL
    )

    private fun on_NEUTRAL_SMALL() = SDGTogglePreviewParameter(
        isOn = true,
        isEnabled = true,
        style = SDGToggleStyle.NEUTRAL,
        spec = SDGToggleSpec.SMALL
    )

    private fun disable_NEUTRAL_SMALL() = SDGTogglePreviewParameter(
        isOn = false,
        isEnabled = false,
        style = SDGToggleStyle.NEUTRAL,
        spec = SDGToggleSpec.SMALL
    )

}

internal data class SDGTogglePreviewParameter(
    val isOn: Boolean,
    val isEnabled: Boolean,
    val style: SDGToggleStyle,
    val spec: SDGToggleSpec,
)