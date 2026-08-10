package com.shopl.sdg.component.attachment_element.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.component.attachment_element.SDGAttachmentElementState
import com.shopl.sdg.component.attachment_element.SDGAttachmentElementType

internal class SDGAttachmentElementPreviewParameterProvider :
    PreviewParameterProvider<SDGAttachmentElementPreviewParams> {

    override val values: Sequence<SDGAttachmentElementPreviewParams> = sequenceOf(
        기본_상태_사진(),
        기본_상태_문서(),
        기본_상태_동영상(),
        업로드_중_사진(),
        업로드_중_문서(),
        업로드_중_동영상(),
        업로드_실패_사진(),
        업로드_실패_문서(),
        업로드_실패_동영상(),
    )

    private fun 기본_상태_사진() = SDGAttachmentElementPreviewParams(
        fileName = "파일의 제목 길어지면...중간 줄임.jpg",
        fileSize = "4MB",
        state = SDGAttachmentElementState.Default,
        type = SDGAttachmentElementType.Photo,
    )

    private fun 기본_상태_문서() = SDGAttachmentElementPreviewParams(
        fileName = "파일의 제목 길어지면...중간 줄임.pdf",
        fileSize = "4MB",
        state = SDGAttachmentElementState.Default,
        type = SDGAttachmentElementType.Document,
    )

    private fun 기본_상태_동영상() = SDGAttachmentElementPreviewParams(
        fileName = "파일의 제목 길어지면...중간 줄임.mp4",
        fileSize = "4MB",
        state = SDGAttachmentElementState.Default,
        type = SDGAttachmentElementType.Video,
    )

    private fun 업로드_중_사진() = SDGAttachmentElementPreviewParams(
        fileName = "파일의 제목 길어지면...중간 줄임.jpg",
        fileSize = "4MB",
        state = SDGAttachmentElementState.Uploading,
        type = SDGAttachmentElementType.Photo,
    )

    private fun 업로드_중_문서() = SDGAttachmentElementPreviewParams(
        fileName = "파일의 제목 길어지면...중간 줄임.pdf",
        fileSize = "4MB",
        state = SDGAttachmentElementState.Uploading,
        type = SDGAttachmentElementType.Document,
    )

    private fun 업로드_중_동영상() = SDGAttachmentElementPreviewParams(
        fileName = "파일의 제목 길어지면...중간 줄임.mp4",
        fileSize = "4MB",
        state = SDGAttachmentElementState.Uploading,
        type = SDGAttachmentElementType.Video,
    )

    private fun 업로드_실패_사진() = SDGAttachmentElementPreviewParams(
        fileName = "파일의 제목 길어지면...중간 줄임.jpg",
        fileSize = "4MB",
        state = SDGAttachmentElementState.Failed,
        type = SDGAttachmentElementType.Photo,
    )

    private fun 업로드_실패_문서() = SDGAttachmentElementPreviewParams(
        fileName = "파일의 제목 길어지면...중간 줄임.pdf",
        fileSize = "4MB",
        state = SDGAttachmentElementState.Failed,
        type = SDGAttachmentElementType.Document,
    )

    private fun 업로드_실패_동영상() = SDGAttachmentElementPreviewParams(
        fileName = "파일의 제목 길어지면...중간 줄임.mp4",
        fileSize = "4MB",
        state = SDGAttachmentElementState.Failed,
        type = SDGAttachmentElementType.Video,
    )
}
