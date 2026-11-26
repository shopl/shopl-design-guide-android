package com.shopl.sdg.template.rich_text.common

import android.webkit.JavascriptInterface
import androidx.annotation.Keep

internal class RichTextMessages(private val onMessage: (String) -> Unit) {
    @Keep
    @JavascriptInterface
    fun postMessage(message: String) {
        onMessage(message)
    }
}
