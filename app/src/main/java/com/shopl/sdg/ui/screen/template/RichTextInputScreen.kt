package com.shopl.sdg.ui.screen.template

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.TemplateScene
import com.shopl.sdg.template.rich_text.input.SDGRichTextInput
import com.shopl.sdg.template.rich_text.input.SDGRichTextInputToolbar
import com.shopl.sdg.template.rich_text.input.SDGRichTextInputState
import com.shopl.sdg.ui.base.SDGSampleBaseGuideLinesContent
import com.shopl.sdg.ui.base.SDGSampleBaseScaffold
import com.shopl.sdg.ui.base.SDGSampleBaseTab
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

private enum class RichTextInputExample(val html: String = "", val maxCount: Int = 500) {
    Default,
    Completed("<h1>제목 1</h1><p>본문에 <strong>서식</strong>을 적용합니다.</p>"),
    Disabled("<p>텍스트 입력</p>"),
    Error("<p>제한 글자 수를 초과한 본문입니다.</p>", maxCount = 10),
}

private val examples = RichTextInputExample.entries.map { SDGSampleBaseTabItem(it.name, it) }.toPersistentList()

/** 입력과 선택 영역 편집, 글자 수 초과 및 키보드 위 툴바를 확인하는 샘플입니다. */
@Composable
internal fun RichTextInputScreen(onClickBack: () -> Unit, onClickMenu: () -> Unit) {
    var selected by rememberSaveable { mutableIntStateOf(0) }
    val states = remember { RichTextInputExample.entries.associateWith { SDGRichTextInputState(it.html) } }
    val example = examples[selected].item
    val state = states.getValue(example)
    Column(Modifier.fillMaxSize().imePadding()) {
        Box(Modifier.weight(1f)) {
            SDGSampleBaseScaffold(
                name = TemplateScene.RichTextInput.displayLabel,
                description = "Rich Text 입력 화면에 배치하여 사용하는, 서식을 적용해 텍스트를 입력하는 입력창 형태의 컴포넌트",
                onClickBack = onClickBack,
                onClickMenu = onClickMenu,
                bodyContent = {
                    Column(
                        modifier = Modifier.padding(SDGSpacing.Spacing16),
                        verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing16),
                    ) {
                        SDGSampleBaseTab("State", examples, selected, { selected = it })
                        SDGRichTextInput(
                            state = state,
                            maxCount = example.maxCount,
                            enabled = example != RichTextInputExample.Disabled,
                        )
                    }
                },
                usageGuideLinesContent = {
                    SDGSampleBaseGuideLinesContent(persistentListOf(
                        "본문을 누르면 키보드 위에 툴바가 나타납니다. 글자를 선택하고 서식이나 링크를 적용할 수 있습니다.",
                        "글자 수가 제한을 넘으면 오류로 표시합니다. Error 탭에서 본문을 줄이면 기본 색상으로 돌아옵니다.",
                    ))
                },
            )
        }
        SDGRichTextInputToolbar(state)
    }
}
