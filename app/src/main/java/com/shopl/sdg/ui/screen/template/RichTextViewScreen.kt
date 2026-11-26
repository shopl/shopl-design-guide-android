package com.shopl.sdg.ui.screen.template

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.TemplateScene
import com.shopl.sdg.template.rich_text.view.SDGRichTextView
import com.shopl.sdg.template.rich_text.view.SDGRichTextViewState
import com.shopl.sdg.ui.base.SDGSampleBaseGuideLinesContent
import com.shopl.sdg.ui.base.SDGSampleBaseScaffold
import com.shopl.sdg.ui.base.SDGSampleBaseTab
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

private const val SCROLL_SAMPLE_PARAGRAPHS = 20

private const val FORMATTED_HTML = """
<h1>제목 1</h1>
<p>기본, <strong>Bold</strong>, <u>밑줄</u>, <s>취소선</s>, <a href="https://www.shoplworks.com">링크</a></p>
<ul><li><p>글머리 기호 목록</p></li></ul>
<ol><li><p>번호 목록</p></li></ol>
<h2>제목 2</h2>
<h3>제목 3</h3>
"""

private enum class RichTextViewExample(
    val title: String,
    val initialHtml: String,
    val initialState: SDGRichTextViewState,
) {
    Input("Default", "", SDGRichTextViewState.Default),
    Completed("Completed", FORMATTED_HTML, SDGRichTextViewState.Completed),
    Disabled("Disabled", "<p>텍스트 입력</p>", SDGRichTextViewState.Disabled),
    Scroll(
        "Scroll",
        FORMATTED_HTML + (1..SCROLL_SAMPLE_PARAGRAPHS).joinToString("") { "<p>스크롤 예시 $it</p>" },
        SDGRichTextViewState.Completed,
    ),
}

private val sampleTabs = RichTextViewExample.entries.map { example ->
    SDGSampleBaseTabItem(title = example.title, item = example)
}.toPersistentList()

/** Rich Text View의 입력 상태, 서식, 비활성 상태와 본문 내부 스크롤을 확인합니다. */
@Composable
internal fun RichTextViewScreen(
    onClickBack: () -> Unit,
    onClickMenu: () -> Unit,
) {
    var selectedTab by rememberSaveable { mutableIntStateOf(0) }

    SDGSampleBaseScaffold(
        name = TemplateScene.RichTextView.displayLabel,
        description = "Rich Text Input 안에 배치하여 사용하는, 서식이 적용된 텍스트를 보여주는 텍스트 영역 형태의 컴포넌트",
        onClickBack = onClickBack,
        onClickMenu = onClickMenu,
        bodyContent = {
            Column(
                modifier = Modifier.fillMaxWidth().padding(SDGSpacing.Spacing16),
                verticalArrangement = Arrangement.spacedBy(SDGSpacing.Spacing16),
            ) {
                SDGSampleBaseTab(
                    tabTitle = "State",
                    tabs = sampleTabs,
                    selectedTabIndex = selectedTab,
                    onTabClick = { selectedTab = it },
                )
                val example = sampleTabs[selectedTab].item
                key(example) {
                    var html by rememberSaveable { mutableStateOf(example.initialHtml) }
                    var state by remember { mutableStateOf(example.initialState) }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, SDGColor.Neutral200)
                            .padding(SDGSpacing.Spacing12)
                            .then(if (example == RichTextViewExample.Scroll) Modifier.height(200.dp) else Modifier),
                        propagateMinConstraints = true,
                    ) {
                        SDGRichTextView(
                            text = html,
                            placeholder = "입력",
                            state = state,
                            onEditorStateChange = { editorState ->
                                html = editorState.html
                                state = when {
                                    example == RichTextViewExample.Disabled -> SDGRichTextViewState.Disabled
                                    editorState.hasFocus -> SDGRichTextViewState.Focused
                                    editorState.isEmpty -> SDGRichTextViewState.Default
                                    else -> SDGRichTextViewState.Completed
                                }
                            },
                        )
                    }
                }
            }
        },
        usageGuideLinesContent = {
            SDGSampleBaseGuideLinesContent(
                guideLineDescriptions = persistentListOf(
                    "Default 탭의 본문을 눌러 Focused 상태를 확인합니다.",
                    "본문 높이는 내용에 맞춰 늘어납니다. Scroll 탭은 Rich Text Input처럼 본문을 200dp로 고정하며, 넘치는 내용은 내부에서 스크롤합니다.",
                ),
            )
        },
    )
}
