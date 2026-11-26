package com.shopl.sdg.template.rich_text.style

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.typography.SDGTypography

internal fun richTextDocument(): String =
    """
    <!doctype html>
    <html>
    <head>
      <meta charset="utf-8">
      <meta name="viewport" content="width=device-width, initial-scale=1">
      <meta http-equiv="Content-Security-Policy"
            content="default-src 'none'; script-src 'self'; style-src 'unsafe-inline'; font-src 'self'">
      <style>
        @font-face { font-family: Pretendard; font-weight: 400; src: url('./regular.otf'); }
        @font-face { font-family: Pretendard; font-weight: 600; src: url('./semibold.otf'); }

        html, body { margin: 0; padding: 0; }
        body {
          --ink: ${SDGColor.Neutral700.cssColor()};
          --link: ${SDGColor.Primary300.cssColor()};
          --highlight: ${SDGColor.YellowY_a10.cssColor()};
          color: var(--ink);
          font-family: Pretendard, sans-serif;
          ${SDGTypography.Body1R.cssTypography()}
          overflow-wrap: break-word;
        }
        .tiptap {
          min-height: 100vh;
          outline: none;
          white-space: pre-wrap;
          caret-color: transparent;
        }
        .tiptap p, .tiptap h1, .tiptap h2, .tiptap h3, .tiptap ul, .tiptap ol {
          margin: 0;
        }
        .tiptap h1 { ${SDGTypography.Point1SB.cssTypography(600)} }
        .tiptap h2 { ${SDGTypography.Point2SB.cssTypography(600)} }
        .tiptap h3 { ${SDGTypography.Title1SB.cssTypography(600)} }
        .tiptap strong { font-weight: 600; }
        .tiptap mark { background-color: var(--highlight); color: inherit; }
        .tiptap a { color: var(--link); text-decoration: underline; }
        .tiptap a, .tiptap u, .tiptap s { text-decoration-skip-ink: none; }
        .tiptap ol, .tiptap ul { padding-inline-start: 24px; }
      </style>
    </head>
    <body>
      <div id="editor"></div>
      <div id="caret" hidden></div>
      <script src="./editor.js" defer></script>
    </body>
    </html>
    """.trimIndent()

private fun SDGTypography.cssTypography(weight: Int = 400): String =
    "font-size: ${style.fontSize.value}px; line-height: ${style.lineHeight.value}px; font-weight: $weight;"

private fun Color.cssColor(): String {
    val argb = toArgb().toUInt().toString(16).padStart(8, '0')
    return "#${argb.substring(2)}${argb.take(2)}"
}
