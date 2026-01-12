package com.shopl.sdg.template.foundation_list.group_and_position_list

data class SDGPositionUiModel(
    val positionId: String,
    val positionName: String,
) {

    companion object {
        internal val FIXTURE = SDGPositionUiModel(
            positionId = "",
            positionName = ""
        )
    }
}
