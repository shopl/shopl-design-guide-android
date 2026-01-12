package com.shopl.sdg.template.foundation_list.group_and_position_list

data class SDGGroupUiModel(
    val groupId: String,
    val groupName: String,
) {

    companion object {
        internal val FIXTURE = SDGGroupUiModel(
            groupId = "",
            groupName = ""
        )
    }
}
