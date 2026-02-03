package com.shopl.sdg.ui.screen.model

import androidx.compose.runtime.Stable
import com.shopl.sdg.scene.SDGScene
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Stable
internal sealed interface MenuItemUiModel {

    data class SectionItem(
        val displayLabel: String
    ) : MenuItemUiModel

    data class GroupItem(
        val displayLabel: String,
        val hasImplementItems: Boolean,
    ) : MenuItemUiModel

    data class SceneItem(
        val scene: SDGScene,
        val isGroupingScene: Boolean = false
    ) : MenuItemUiModel
}

internal fun MenuSectionUiModel.toMenuItemUiModels(
    expandedGroups: Set<String>
): ImmutableList<MenuItemUiModel> {
    val menuItems = mutableListOf<MenuItemUiModel>()
    menuItems.add(MenuItemUiModel.SectionItem(this.sectionName))
    menus.forEach { menu ->
        when (menu) {
            is MenuUiModel.GroupMenu -> {
                menuItems.add(
                    MenuItemUiModel.GroupItem(
                    displayLabel = menu.groupName,
                    hasImplementItems = menu.sceneMenus.any { it.scene.implemented }
                ))
                if (expandedGroups.contains(menu.groupName)) {
                    menu.sceneMenus.forEach { sceneMenu ->
                        menuItems.add(
                            MenuItemUiModel.SceneItem(
                                scene = sceneMenu.scene,
                                isGroupingScene = true
                            )
                        )
                    }
                }
            }

            is MenuUiModel.SceneMenu -> {
                menuItems.add(MenuItemUiModel.SceneItem(menu.scene))
            }
        }
    }
    return menuItems.toImmutableList()
}
