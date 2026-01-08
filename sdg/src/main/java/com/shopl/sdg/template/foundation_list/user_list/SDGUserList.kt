package com.shopl.sdg.template.foundation_list.user_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.shopl.sdg.component.avatar.SDGAvatarBadge
import com.shopl.sdg.template.popup.center.SDGCenterPopupButtonOption
import com.shopl.sdg.template.profile.second.SDGSecondProfile
import com.shopl.sdg.template.profile.second.SDGSecondProfileType
import com.shopl.sdg.template.util.popup.SDGCenterPopup
import com.shopl.sdg_common.foundation.SDGColor
import com.shopl.sdg_common.foundation.spacing.SDGSpacing
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.toPersistentList

/**
 * SDG - Template - User List
 *
 * 사용자 목록을 Center Popup 형태로 노출하는 컴포넌트입니다.
 *
 * 각 사용자 항목은 프로필 정보와 아바타 영역을 포함하며,
 * 하단에는 단일 액션 버튼이 고정되어 노출됩니다.
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=19877-34076&m=dev">Figma</a>
 */
@Composable
fun SDGUserList(
    popupTitle: String,
    buttonLabel: String,
    users: PersistentList<SDGUserUiModel>,
    onClickButton: () -> Unit,
    onClickAvatar: (() -> Unit)? = null,
    onClickProfile: (() -> Unit)? = null
) {
    SDGCenterPopup(
        buttonOption = SDGCenterPopupButtonOption.OneOption(
            label = buttonLabel,
            labelColor = SDGColor.Neutral700,
            onClick = onClickButton,
        ),
        title = popupTitle,
        itemSpacing = SDGSpacing.Spacing16,
    ) {
        items(items = users) { user ->
            SDGSecondProfile(
                userRegImg = user.profileUserRegImg,
                userName = user.profileUserName,
                groupName = user.profileGroupName,
                backgroundColor = user.profileBackgroundColor,
                type = user.profileType,
                avatarBadge = user.profileAvatarBadge,
                onClickAvatar = onClickAvatar,
                onClickProfile = onClickProfile,
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSDGUserList() {
    Box(modifier = Modifier.fillMaxSize()) {
        SDGUserList(
            popupTitle = "User",
            buttonLabel = "Label",
            users = List(size = 100) {
                SDGUserUiModel(
                    profileUserRegImg = null,
                    profileUserName = "직원명",
                    profileGroupName = "그룹명",
                    profileBackgroundColor = SDGColor.Transparent,
                    profileType = SDGSecondProfileType.Normal,
                    profileAvatarBadge = SDGAvatarBadge.Leader,
                )
            }.toPersistentList(),
            onClickButton = {},
        )
    }
}
