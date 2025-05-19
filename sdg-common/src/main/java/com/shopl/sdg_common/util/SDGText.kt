package com.shopl.sdg_common.util

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography

/**
 * 공통 [Text] - [String]
 *
 * @param textAlign      텍스트 정렬 방식 (기본값은 Start)
 * @param overflow       텍스트가 영역을 벗어날 때 처리 방식 (기본값은 Clip)
 * @param letterSpacing  문자 간격 (기본값은 0.sp)
 * @param textDecoration 텍스트 장식(밑줄, 취소선 등)
 * @param onTextLayout   텍스트 레이아웃 결과 콜백
 * @param softWrap       단어 단위 줄바꿈 여부 (기본값은 true)
 */
@Composable
fun SDGText(
    modifier: Modifier = Modifier,
    text: String,
    typography: SDGTypography,
    textColor: Color,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Clip,
    letterSpacing: TextUnit = 0.sp,
    textDecoration: TextDecoration? = null,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    softWrap: Boolean = true
) {
    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        letterSpacing = letterSpacing,
        fontWeight = null,
        fontStyle = null,
        textDecoration = textDecoration,
        textAlign = textAlign,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        style = typography.style
    )
}

/**
 * 공통 [Text] - [AnnotatedString]
 *
 * @param textAlign      텍스트 정렬 방식 (기본값은 Start)
 * @param overflow       텍스트가 영역을 벗어날 때 처리 방식 (기본값은 Clip)
 * @param letterSpacing  문자 간격 (기본값은 0.sp)
 * @param textDecoration 텍스트 장식(밑줄, 취소선 등)
 * @param onTextLayout   텍스트 레이아웃 결과 콜백
 * @param softWrap       단어 단위 줄바꿈 여부 (기본값은 true)
 */
@Composable
fun SDGText(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    typography: SDGTypography,
    textColor: Color,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Clip,
    letterSpacing: TextUnit = 0.sp,
    textDecoration: TextDecoration? = null,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    softWrap: Boolean = true
) {
    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        letterSpacing = letterSpacing,
        fontWeight = null,
        fontStyle = null,
        textDecoration = textDecoration,
        textAlign = textAlign,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        style = typography.style
    )
}

@Preview(name = "NaviTitle", group = "SDGTypography")
@Composable
fun PreviewNaviTitle() {
    SDGText(
        text = "NaviTitle",
        typography = SDGTypography.NaviTitle,
        textColor = SDGColor.Neutral0
    )
}

@Preview(name = "Title1SB", group = "SDGTypography")
@Composable
fun PreviewTitle1SB() {
    SDGText(
        text = "Title1SB",
        typography = SDGTypography.Title1SB,
        textColor = SDGColor.Neutral0
    )
}

@Preview(name = "Title1R", group = "SDGTypography")
@Composable
fun PreviewTitle1R() {
    SDGText(
        text = "Title1R",
        typography = SDGTypography.Title1R,
        textColor = SDGColor.Neutral0
    )
}

@Preview(name = "Body1SB", group = "SDGTypography")
@Composable
fun PreviewBody1SB() {
    SDGText(
        text = "Body1SB",
        typography = SDGTypography.Body1SB,
        textColor = SDGColor.Neutral0
    )
}

@Preview(name = "Body1R", group = "SDGTypography")
@Composable
fun PreviewBody1R() {
    SDGText(
        text = "Body1R",
        typography = SDGTypography.Body1R,
        textColor = SDGColor.Neutral0
    )
}

@Preview(name = "Body2SB", group = "SDGTypography")
@Composable
fun PreviewBody2SB() {
    SDGText(
        text = "Body2SB",
        typography = SDGTypography.Body2SB,
        textColor = SDGColor.Neutral0
    )
}

@Preview(name = "Body2R", group = "SDGTypography")
@Composable
fun PreviewBody2R() {
    SDGText(
        text = "Body2R",
        typography = SDGTypography.Body2R,
        textColor = SDGColor.Neutral0
    )
}

@Preview(name = "Body3SB", group = "SDGTypography")
@Composable
fun PreviewBody3SB() {
    SDGText(
        text = "Body3SB",
        typography = SDGTypography.Body3SB,
        textColor = SDGColor.Neutral0
    )
}

@Preview(name = "Body3R", group = "SDGTypography")
@Composable
fun PreviewBody3R() {
    SDGText(
        text = "Body3R",
        typography = SDGTypography.Body3R,
        textColor = SDGColor.Neutral0
    )
}