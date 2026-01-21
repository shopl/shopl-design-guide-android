package com.shopl.sdg.ui.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shopl.sdg.enums.SDGSampleStatus
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.ui.common.SDGSampleSpecTab
import com.shopl.sdg.ui.common.SDGSampleStatusBox
import com.shopl.sdg.ui.common.SDGSampleTypeTab
import com.shopl.sdg.ui.theme.ShoplDesignGuideTheme
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import com.shopl.sdg_common.foundation.typography.SDGTypography
import com.shopl.sdg_common.ui.components.SDGText
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG Component 샘플 페이지 Base Scaffold
 *
 * @param componentName 해당 컴포넌트의 이름
 * @param componentDescription 해당 컴포넌트의 설명
 * @param types 샘플앱에 표기되는 Type 정보
 * @param specs 샘플앱에 표기되는 Size Spec 정보
 * @param componentContent 해당 컴포넌트를 그리는 영역. 선택된 type, spec, status를 인자로 받습니다.
 * @param guideLineDescriptions 가이드 라인에 대한 설명
 */
@Composable
internal fun <TYPE, SPEC> SDGSampleBaseComponentScaffold(
    componentName: String,
    componentDescription: String,
    types: PersistentList<SDGSampleBaseTabItem<TYPE>>? = null,
    specs: PersistentList<SDGSampleBaseTabItem<SPEC>>? = null,
    componentContent: @Composable (type: TYPE?, spec: SPEC?, status: SDGSampleStatus) -> Unit,
    guideLineDescriptions: PersistentList<String> = persistentListOf()
) {
    SDGSampleBaseScaffold(
        name = componentName,
        description = componentDescription,
        bodyContent = {
            BodyContent(
                types = types,
                specs = specs,
                componentContent = componentContent
            )
        },
        usageGuideLinesContent = guideLineDescriptions.takeIf { it.isNotEmpty() }
            ?.let { descriptions ->
                @Composable {
                    SDGSampleBaseGuideLinesContent(guideLineDescriptions = descriptions)
                }
            }
    )
}

@Composable
private fun <TYPE, SPEC> BodyContent(
    types: PersistentList<SDGSampleBaseTabItem<TYPE>>?,
    specs: PersistentList<SDGSampleBaseTabItem<SPEC>>?,
    componentContent: @Composable (currentType: TYPE?, currentSpec: SPEC?, currentStatus: SDGSampleStatus) -> Unit
) {
    var selectedTypeIndex by remember { mutableIntStateOf(0) }
    var selectedSpecIndex by remember { mutableIntStateOf(0) }
    var selectedStatus by remember { mutableStateOf(SDGSampleStatus.DEFAULT) }

    LaunchedEffect(types?.size) {
        if (!types.isNullOrEmpty() && selectedTypeIndex >= types.size) {
            selectedTypeIndex = 0
        }
    }
    LaunchedEffect(specs?.size) {
        if (!specs.isNullOrEmpty() && selectedSpecIndex >= specs.size) {
            selectedSpecIndex = 0
        }
    }

    val currentType by remember(types) {
        derivedStateOf { types?.getOrNull(selectedTypeIndex)?.item }
    }
    val currentSpec by remember(specs) {
        derivedStateOf { specs?.getOrNull(selectedSpecIndex)?.item }
    }

    if (types.isNullOrEmpty() && specs.isNullOrEmpty()) {
        componentContent(null, null, selectedStatus)
        return
    }

    val tabModifier = Modifier.padding(
        start = SDGSpacing.Spacing16,
        top = SDGSpacing.Spacing16,
        end = SDGSpacing.Spacing16,
        bottom = SDGSpacing.Spacing12
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        if (!types.isNullOrEmpty()) {
            SDGSampleTypeTab(
                modifier = tabModifier,
                tabs = types,
                selectedTabIndex = selectedTypeIndex,
                onTabClick = { selectedTypeIndex = it }
            )
        }

        if (!types.isNullOrEmpty() && !specs.isNullOrEmpty()) {
            HorizontalDivider(color = SDGColor.Neutral200)
        }

        if (!specs.isNullOrEmpty()) {
            SDGSampleSpecTab(
                modifier = tabModifier,
                tabs = specs,
                selectedTabIndex = selectedSpecIndex,
                onTabClick = { selectedSpecIndex = it }
            )
        }
    }

    SDGSampleStatusBox(
        modifier = Modifier.padding(
            start = SDGSpacing.Spacing16,
            end = SDGSpacing.Spacing16,
            bottom = SDGSpacing.Spacing16
        ),
        currentStatus = selectedStatus,
        onClickStatus = { selectedStatus = it },
        content = {
            componentContent(currentType, currentSpec, selectedStatus)
        }
    )
}

@Preview
@Composable
private fun PreviewSDGSampleBaseComponentScaffold() {
    ShoplDesignGuideTheme {
        SDGSampleBaseComponentScaffold(
            componentName = "SDG Component",
            componentDescription = "SDG Component Description",
            types = persistentListOf(
                SDGSampleBaseTabItem("Type 1", 0),
                SDGSampleBaseTabItem("Type 2", 1),
            ),
            specs = persistentListOf(
                SDGSampleBaseTabItem("Spec 1", 0),
                SDGSampleBaseTabItem("Spec 2", 1),
            ),
            componentContent = { currentType, currentSpec, currentStatus ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(SDGColor.Neutral150),
                    contentAlignment = Alignment.Center
                ) {
                    SDGText(
                        text = "Type: $currentType, Spec: $currentSpec, Status: $currentStatus",
                        textColor = SDGColor.Neutral700,
                        typography = SDGTypography.Body3R
                    )
                }
            },
            guideLineDescriptions = persistentListOf("Usage GuideLine Description")
        )
    }
}
