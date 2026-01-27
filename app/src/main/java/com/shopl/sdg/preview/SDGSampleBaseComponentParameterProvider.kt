package com.shopl.sdg.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.model.SDGSampleBaseTabItem
import kotlinx.collections.immutable.persistentListOf

internal class SDGSampleBaseComponentParameterProvider :
    PreviewParameterProvider<SDGSampleBaseComponentUiState> {

    override val values = sequenceOf(
        Type_Spec_GuideLine_존재하는_경우(),
        Type_Spec_존재하는_경우(),
        Type_GuideLine_존재하는_경우(),
        Spec만_존재하는_경우(),
    )

    private fun Type_Spec_GuideLine_존재하는_경우(): SDGSampleBaseComponentUiState {
        return SDGSampleBaseComponentUiState(
            componentName = "SDG Component",
            componentDescription = "Type, Spec, GuideLine 모두 존재하는 Component",
            types = persistentListOf(
                SDGSampleBaseTabItem("Type 1", 0),
                SDGSampleBaseTabItem("Type 2", 1),
            ),
            specs = persistentListOf(
                SDGSampleBaseTabItem("Spec 1", 0),
                SDGSampleBaseTabItem("Spec 2", 1),
            ),
            guideLineDescriptions = persistentListOf("Usage GuideLine Description")
        )
    }

    private fun Type_Spec_존재하는_경우(): SDGSampleBaseComponentUiState {
        return SDGSampleBaseComponentUiState(
            componentName = "SDG Component",
            componentDescription = "Type, Spec 존재하는 Component",
            types = persistentListOf(
                SDGSampleBaseTabItem("Type 1", 0),
                SDGSampleBaseTabItem("Type 2", 1),
            ),
            specs = persistentListOf(
                SDGSampleBaseTabItem("Spec 1", 0),
                SDGSampleBaseTabItem("Spec 2", 1),
            ),
        )
    }

    private fun Type_GuideLine_존재하는_경우(): SDGSampleBaseComponentUiState {
        return SDGSampleBaseComponentUiState(
            componentName = "SDG Component",
            componentDescription = "Type, GuideLine 존재하는 Component",
            types = persistentListOf(
                SDGSampleBaseTabItem("Type 1", 0),
                SDGSampleBaseTabItem("Type 2", 1),
            ),
            specs = null,
            guideLineDescriptions = persistentListOf("Usage GuideLine Description")
        )
    }

    private fun Spec만_존재하는_경우(): SDGSampleBaseComponentUiState {
        return SDGSampleBaseComponentUiState(
            componentName = "SDGComponent",
            componentDescription = "Spec만 존재하는 Component",
            types = null,
            specs = persistentListOf(
                SDGSampleBaseTabItem("Spec 1", 0),
                SDGSampleBaseTabItem("Spec 2", 1),
            ),
        )
    }
}
