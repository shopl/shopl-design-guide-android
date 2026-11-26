package com.shopl.sdg.ui.screen.template

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.shopl.sdg.model.SDGSampleBaseTabItem
import com.shopl.sdg.scene.TemplateScene
import com.shopl.sdg.template.rich_text.view.SDGRichTextView
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
    val html: String,
    val bodyHeightModifier: Modifier = Modifier.heightIn(max = 200.dp),
) {
    Plain("Text", "<p>한 줄 본문입니다.</p>"),
    Formatted("Formatted", FORMATTED_HTML),
    Scroll(
        "Scroll",
        FORMATTED_HTML + (1..SCROLL_SAMPLE_PARAGRAPHS).joinToString("") { "<p>스크롤 예시 $it</p>" },
        Modifier.height(200.dp),
    ),
}

private val sampleTabs = RichTextViewExample.entries.map { example ->
    SDGSampleBaseTabItem(title = example.title, item = example)
}.toPersistentList()

/** Rich Text View의 일반 본문과 서식, 본문 내부 스크롤을 확인합니다. */
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
                    tabTitle = "Content",
                    tabs = sampleTabs,
                    selectedTabIndex = selectedTab,
                    onTabClick = { selectedTab = it },
                )
                val example = sampleTabs[selectedTab].item
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, SDGColor.Neutral200)
                        .padding(SDGSpacing.Spacing12)
                        .then(example.bodyHeightModifier),
                    propagateMinConstraints = true,
                ) {
                    SDGRichTextView(text = example.html)
                }
            }
        },
        usageGuideLinesContent = {
            SDGSampleBaseGuideLinesContent(
                guideLineDescriptions = persistentListOf(
                    "Text와 Formatted 탭은 본문 길이에 맞춰 최대 200dp까지 표시합니다.",
                    "Scroll 탭은 Rich Text Input처럼 처음부터 본문 높이를 200dp로 고정합니다.",
                ),
            )
        },
    )
}
