package com.shopl.sdg.template.rich_text.common

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.MotionEvent
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import com.shopl.sdg.template.rich_text.style.richTextDocument
import com.shopl.sdg_resource.R
import org.json.JSONException
import org.json.JSONObject
import java.io.ByteArrayInputStream

private const val TAG = "SDGRichTextView"
private const val BRIDGE = "SDGRichTextHost"
private const val HOST = "appassets.androidplatform.net"
private const val ASSET_DIRECTORY = "shopl/sdg/rich-text"
private const val PATH = "/$ASSET_DIRECTORY"

@SuppressLint("SetJavaScriptEnabled")
internal class RichTextWebView(context: Context) : WebView(context) {
    private var ready = false
    private var released = false
    private var lastHtml: String? = null

    init {
        setBackgroundColor(android.graphics.Color.TRANSPARENT)
        isHorizontalScrollBarEnabled = false
        isVerticalScrollBarEnabled = false
        scrollBarStyle = SCROLLBARS_INSIDE_OVERLAY
        settings.apply {
            javaScriptEnabled = true
            allowFileAccess = false
            allowContentAccess = false
        }

        webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest,
            ): Boolean {
                val url = request.url
                if (request.isForMainFrame && request.hasGesture() &&
                    (url.scheme == "https" || url.scheme == "http") &&
                    !url.host.isNullOrBlank() && !HOST.equals(url.host, ignoreCase = true)
                ) {
                    openInBrowser(url)
                }
                return true
            }

            override fun shouldInterceptRequest(
                view: WebView,
                request: WebResourceRequest
            ): WebResourceResponse {
                val url = request.url
                if (url.scheme != "https" || url.host != HOST || url.port != -1) {
                    return blockedResponse()
                }

                return when (url.path) {
                    "$PATH/index.html" -> response("text/html", richTextDocument())
                    "$PATH/editor.js" -> WebResourceResponse(
                        "text/javascript",
                        "UTF-8",
                        context.assets.open("$ASSET_DIRECTORY/editor.js")
                    )

                    "$PATH/regular.otf" -> fontResponse(
                        R.font.pretendard_regular,
                        R.font.pretendard_jp_regular
                    )

                    "$PATH/semibold.otf" -> fontResponse(
                        R.font.pretendard_semibold,
                        R.font.pretendard_jp_semibold
                    )

                    else -> blockedResponse()
                }
            }
        }

        addJavascriptInterface(RichTextMessages { message ->
            post {
                if (!released) receive(message)
            }
        }, BRIDGE)
        loadUrl("https://$HOST$PATH/index.html")
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.actionMasked == MotionEvent.ACTION_DOWN &&
            (canScrollVertically(-1) || canScrollVertically(1))
        ) {
            // 긴 본문을 스크롤할 때 상위 Compose 스크롤 영역이 제스처를 가로채지 않게 한다.
            parent?.requestDisallowInterceptTouchEvent(true)
        }
        return super.onTouchEvent(event)
    }

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        if (t > 0 && t != oldt && !isVerticalScrollBarEnabled) {
            isVerticalScrollBarEnabled = true
        }
        super.onScrollChanged(l, t, oldl, oldt)
    }

    fun render(html: String) {
        if (html == lastHtml) return

        isVerticalScrollBarEnabled = false
        lastHtml = html
        if (ready) displayContent()
    }

    private fun receive(raw: String) {
        try {
            val message = JSONObject(raw)
            when (message.getString("type")) {
                "ready" -> {
                    ready = true
                    displayContent()
                }

                "error" -> Log.e(TAG, "Rich text editor update failed.")
            }
        } catch (_: JSONException) {
            Log.e(TAG, "Invalid message from the rich text editor.")
        }
    }

    private fun displayContent() {
        val html = lastHtml ?: return
        val update = JSONObject().put("html", html).put("enabled", false)
        evaluateJavascript(
            """
            (function() {
                window.sdgRichText.update(${JSONObject.quote(update.toString())});
                const content = document.querySelector('.tiptap');
                if (content) {
                    content.removeAttribute('role');
                    content.removeAttribute('aria-label');
                    content.removeAttribute('aria-multiline');
                }
            })()
            """.trimIndent(),
            null,
        )
    }

    private fun openInBrowser(url: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, url).apply {
            addCategory(Intent.CATEGORY_BROWSABLE)
            selector = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_APP_BROWSER)
            if (context !is Activity) addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Log.e(TAG, "No browser available to open rich text link.", e)
        } catch (e: SecurityException) {
            Log.e(TAG, "Unable to open rich text link.", e)
        }
    }

    private fun fontResponse(font: Int, japaneseFont: Int): WebResourceResponse {
        val japanese = resources.configuration.locales[0]?.language == "ja"
        return WebResourceResponse(
            "font/otf",
            null,
            resources.openRawResource(if (japanese) japaneseFont else font)
        )
    }

    fun release() {
        released = true
        removeJavascriptInterface(BRIDGE)
        destroy()
    }
}

private fun response(mimeType: String, content: String) =
    WebResourceResponse(
        mimeType,
        "UTF-8",
        ByteArrayInputStream(content.toByteArray(Charsets.UTF_8))
    )

private fun blockedResponse() = WebResourceResponse(
    "text/plain", "UTF-8", 403, "Forbidden", emptyMap(), ByteArrayInputStream(byteArrayOf())
)
