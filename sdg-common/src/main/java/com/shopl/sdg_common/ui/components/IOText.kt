package com.shopl.sdg_common.ui.components

import android.graphics.Typeface
import androidx.annotation.ColorRes
import androidx.compose.foundation.text.InlineTextContent
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

/**
 * 기존 디자인 시스템 내부에서 사용하기 위한 컴포저블 [IOText]
 * 작업 분배 후 제거 예정
 */
@Deprecated("레거시")
enum class IOTypeface {
    REGULAR, SEMI_BOLD;

    val fontFamily: FontFamily by lazy {
        FontFamily(
            typeface = when (this) {
                REGULAR -> Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
                SEMI_BOLD -> Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
            }
        )
    }
}

object TypefaceConfig {
    var normal: Typeface = Typeface.create(Typeface.DEFAULT, Typeface.NORMAL)
    var bold: Typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
}

/**
 * String(text), ColorResourceId(textColor)를 사용하는 IOText
 */
@Deprecated("ColorResourceId는 사용하지 않음", ReplaceWith("IOColor를 사용하세요"))
@Composable
fun IOText(
    text: String,
    modifier: Modifier = Modifier,
    @ColorRes
    textColor: Int,
    fontSize: TextUnit,
    typeface: IOTypeface = IOTypeface.REGULAR,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Clip,
    letterSpacing: TextUnit = 0.sp,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    softWrap: Boolean = true,
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        text = text,
        modifier = modifier,
        color = colorResource(id = textColor),
        fontSize = fontSize,
        fontStyle = null,
        fontWeight = null,
        fontFamily = FontFamily(
            typeface = when (typeface) {
                IOTypeface.REGULAR -> TypefaceConfig.normal
                IOTypeface.SEMI_BOLD -> TypefaceConfig.bold
            }
        ),
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        style = style
    )
}

/**
 * AnnotatedString(text), ColorResourceId(textColor)를 사용하는 IOText
 */
@Deprecated("ColorResourceId는 사용하지 않음", ReplaceWith("IOColor를 사용하세요"))
@Composable
fun IOText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    @ColorRes
    textColor: Int,
    fontSize: TextUnit,
    typeface: IOTypeface = IOTypeface.REGULAR,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Clip,
    letterSpacing: TextUnit = 0.sp,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    softWrap: Boolean = true,
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        text = text,
        modifier = modifier,
        color = colorResource(id = textColor),
        fontSize = fontSize,
        fontStyle = null,
        fontWeight = null,
        fontFamily = FontFamily(
            typeface = when (typeface) {
                IOTypeface.REGULAR -> TypefaceConfig.normal
                IOTypeface.SEMI_BOLD -> TypefaceConfig.bold
            }
        ),
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        style = style
    )
}

/**
 * String(text)을 사용하는 IOText
 */
@Composable
fun IOText(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color,
    fontSize: TextUnit,
    typeface: IOTypeface = IOTypeface.REGULAR,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Clip,
    letterSpacing: TextUnit = 0.sp,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    softWrap: Boolean = true,
    style: TextStyle = LocalTextStyle.current
) {
    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        fontSize = fontSize,
        fontStyle = null,
        fontWeight = null,
        fontFamily = FontFamily(
            typeface = when (typeface) {
                IOTypeface.REGULAR -> TypefaceConfig.normal
                IOTypeface.SEMI_BOLD -> TypefaceConfig.bold
            }
        ),
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        style = style
    )
}

/**
 * AnnotatedString(text)을 사용하는 IOText
 */
@Composable
fun IOText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    textColor: Color,
    fontSize: TextUnit,
    typeface: IOTypeface = IOTypeface.REGULAR,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign = TextAlign.Start,
    overflow: TextOverflow = TextOverflow.Clip,
    letterSpacing: TextUnit = 0.sp,
    lineHeight: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    softWrap: Boolean = true,
    style: TextStyle = LocalTextStyle.current,
    inlineContent: Map<String, InlineTextContent> = mapOf(),
) {
    Text(
        text = text,
        modifier = modifier,
        color = textColor,
        fontSize = fontSize,
        fontStyle = null,
        fontWeight = null,
        fontFamily = FontFamily(
            typeface = when (typeface) {
                IOTypeface.REGULAR -> TypefaceConfig.normal
                IOTypeface.SEMI_BOLD -> TypefaceConfig.bold
            }
        ),
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        style = style,
        inlineContent = inlineContent
    )
}