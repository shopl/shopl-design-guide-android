package com.shopl.sdg.ui.screen.model

import com.shopl.sdg.scene.SDGScene
import kotlinx.collections.immutable.ImmutableList

internal sealed interface MenuUiModel {

    data class GroupMenu(
        val groupName: String,
        val sceneMenus: ImmutableList<SceneMenu>,
    ) : MenuUiModel

    data class SceneMenu(
        val scene: SDGScene,
    ) : MenuUiModel

}

internal fun SDGScene.toMenuUiModel() = MenuUiModel.SceneMenu(this)
