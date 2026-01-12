package com.shopl.sdg.template.foundation_list.group_and_position_list.preview

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.shopl.sdg.template.foundation_list.group_and_position_list.SDGGroupUiModel
import com.shopl.sdg.template.foundation_list.group_and_position_list.SDGPositionUiModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList

internal class SDGGroupAndPositionPreviewParameterProvider :
    PreviewParameterProvider<SDGGroupAndPositionPreviewParams> {

    override val values: Sequence<SDGGroupAndPositionPreviewParams> = sequenceOf(
        그룹만_존재하는_경우(),
        포지션만_존재하는_경우(),
        그룹과_포지션이_모두_존재하는_경우(),
        그룹과_포지션이_각각_10개_이상_존재하는_경우()
    )

    private fun 그룹만_존재하는_경우() = SDGGroupAndPositionPreviewParams(
        groups = persistentListOf(
            SDGGroupUiModel.FIXTURE.copy(groupName = "그룹 1")
        ),
        positions = persistentListOf(),
    )

    private fun 포지션만_존재하는_경우() = SDGGroupAndPositionPreviewParams(
        groups = persistentListOf(),
        positions = persistentListOf(
            SDGPositionUiModel.FIXTURE.copy(positionName = "포지션 1")
        ),
    )

    private fun 그룹과_포지션이_모두_존재하는_경우() = SDGGroupAndPositionPreviewParams(
        groups = persistentListOf(
            SDGGroupUiModel.FIXTURE.copy(groupName = "그룹 1")
        ),
        positions = persistentListOf(
            SDGPositionUiModel.FIXTURE.copy(positionName = "포지션 1")
        ),
    )

    private fun 그룹과_포지션이_각각_10개_이상_존재하는_경우() = SDGGroupAndPositionPreviewParams(
        groups = List(size = 10) {
            SDGGroupUiModel.FIXTURE.copy(groupName = "그룹 1")
        }.toPersistentList(),
        positions = List(size = 10) {
            SDGPositionUiModel.FIXTURE.copy(positionName = "포지션 1")
        }.toPersistentList(),
    )
}
