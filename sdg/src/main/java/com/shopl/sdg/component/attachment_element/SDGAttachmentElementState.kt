package com.shopl.sdg.component.attachment_element

sealed interface SDGAttachmentElementState {
    val title: String

    data object Default : SDGAttachmentElementState {
        override val title = "Default"
    }

    data object Uploading : SDGAttachmentElementState {
        override val title = "Uploading"
    }

    data object Failed : SDGAttachmentElementState {
        override val title = "Failed"
    }
}
