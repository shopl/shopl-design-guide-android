package com.shopl.sdg.ui.screen.model

import com.shopl.sdg.scene.ComponentScene
import com.shopl.sdg.scene.FoundationScene
import com.shopl.sdg.scene.TemplateScene
import kotlinx.collections.immutable.persistentListOf

internal val foundationMenuSection = MenuSectionUiModel(
    sectionName = "Foundation",
    menus = persistentListOf(
        FoundationScene.Color.toMenuUiModel(),
        FoundationScene.CornerRadius.toMenuUiModel(),
        FoundationScene.Iconography.toMenuUiModel(),
        FoundationScene.Spacing.toMenuUiModel(),
        FoundationScene.Typograph.toMenuUiModel(),
    )
)

internal val componentMenuSection = MenuSectionUiModel(
    sectionName = "Component",
    menus = persistentListOf(
        ComponentScene.Avatar.toMenuUiModel(),
        ComponentScene.AttachmentList.toMenuUiModel(),
        MenuUiModel.GroupMenu(
            groupName = "Button",
            sceneMenus = persistentListOf(
                ComponentScene.Button.BottomButton.toMenuUiModel(),
                ComponentScene.Button.BoxButton.toMenuUiModel(),
                ComponentScene.Button.CapsuleButton.toMenuUiModel(),
                ComponentScene.Button.FloatingButton.toMenuUiModel(),
                ComponentScene.Button.GhostButton.toMenuUiModel(),
            )
        ),
        ComponentScene.Calendar.toMenuUiModel(),
        ComponentScene.Checkbox.toMenuUiModel(),
        ComponentScene.CheckOption.toMenuUiModel(),
        ComponentScene.Dropdown.toMenuUiModel(),
        MenuUiModel.GroupMenu(
            groupName = "Empty Icon",
            sceneMenus = persistentListOf(
                ComponentScene.EmptyIcon.BasicEmptyIcon.toMenuUiModel(),
                ComponentScene.EmptyIcon.ContentsEmptyIcon.toMenuUiModel(),
            )
        ),
        MenuUiModel.GroupMenu(
            groupName = "Indicator",
            sceneMenus = persistentListOf(
                ComponentScene.Indicator.TextIndicator.toMenuUiModel(),
                ComponentScene.Indicator.NumberIndicator.toMenuUiModel(),
                ComponentScene.Indicator.DotIndicator.toMenuUiModel(),
            )
        ),
        ComponentScene.IconLabel.toMenuUiModel(),
        ComponentScene.NumberPicker.toMenuUiModel(),
        MenuUiModel.GroupMenu(
            groupName = "Progress",
            sceneMenus = persistentListOf(
                ComponentScene.Progress.SystemProgress.toMenuUiModel(),
                ComponentScene.Progress.CircularProgress.toMenuUiModel(),
                ComponentScene.Progress.DotProgress.toMenuUiModel(),
                ComponentScene.Progress.LinearProgress.toMenuUiModel(),
            )
        ),
        ComponentScene.Radio.toMenuUiModel(),
        MenuUiModel.GroupMenu(
            groupName = "Search Bar",
            sceneMenus = persistentListOf(
                ComponentScene.SearchBar.CapsuleSearch.toMenuUiModel(),
                ComponentScene.SearchBar.BoxSearch.toMenuUiModel(),
                ComponentScene.SearchBar.CategorySearch.toMenuUiModel(),
            )
        ),
        ComponentScene.Segment.toMenuUiModel(),
        ComponentScene.SelectInput.toMenuUiModel(),
        MenuUiModel.GroupMenu(
            groupName = "Tab",
            sceneMenus = persistentListOf(
                ComponentScene.Tab.ScrollTab.toMenuUiModel(),
                ComponentScene.Tab.FixedTab.toMenuUiModel(),
                ComponentScene.Tab.BoxTab.toMenuUiModel(),
                ComponentScene.Tab.IconTab.toMenuUiModel(),
            )
        ),
        MenuUiModel.GroupMenu(
            groupName = "Text Input",
            sceneMenus = persistentListOf(
                ComponentScene.TextInput.SimpleTextInput.toMenuUiModel(),
                ComponentScene.TextInput.FixedTextInput.toMenuUiModel(),
                ComponentScene.TextInput.UnderlineInput.toMenuUiModel(),
                ComponentScene.TextInput.LoginInput.toMenuUiModel(),
            )
        ),
        ComponentScene.Thumbnails.toMenuUiModel(),
        ComponentScene.TimePicker.toMenuUiModel(),
        ComponentScene.TimeSelectInput.toMenuUiModel(),
        ComponentScene.Toggle.toMenuUiModel(),
        ComponentScene.Tooltip.toMenuUiModel(),
    )
)

internal val templateMenuSection = MenuSectionUiModel(
    sectionName = "Template",
    menus = persistentListOf(
        TemplateScene.CalendarAndTime.toMenuUiModel(),
        TemplateScene.CheckboxLabel.toMenuUiModel(),
        TemplateScene.CheckOptionLabel.toMenuUiModel(),
        TemplateScene.EmptyImg.toMenuUiModel(),
        MenuUiModel.GroupMenu(
            groupName = "Filter Chip",
            sceneMenus = persistentListOf(
                TemplateScene.FilterChip.NaviFilterChip.toMenuUiModel(),
                TemplateScene.FilterChip.BodyFilterChip.toMenuUiModel(),
            )
        ),
        MenuUiModel.GroupMenu(
            groupName = "Form",
            sceneMenus = persistentListOf(
                TemplateScene.Form.DropdownForm.toMenuUiModel(),
                TemplateScene.Form.SelectForm.toMenuUiModel(),
                TemplateScene.Form.SimpleTextForm.toMenuUiModel(),
                TemplateScene.Form.FixedTextForm.toMenuUiModel(),
                TemplateScene.Form.TimeSelectForm.toMenuUiModel(),
            )
        ),
        MenuUiModel.GroupMenu(
            groupName = "Foundation List",
            sceneMenus = persistentListOf(
                TemplateScene.FoundationList.UserList.toMenuUiModel(),
                TemplateScene.FoundationList.WorkplaceList.toMenuUiModel(),
                TemplateScene.FoundationList.GroupAndPositionList.toMenuUiModel(),
            )
        ),
        TemplateScene.History.toMenuUiModel(),
        TemplateScene.HistoryMini.toMenuUiModel(),
        TemplateScene.ListHeader.toMenuUiModel(),
        TemplateScene.MultiCalendar.toMenuUiModel(),
        TemplateScene.MultiTimePicker.toMenuUiModel(),
        MenuUiModel.GroupMenu(
            groupName = "Navigation",
            sceneMenus = persistentListOf(
                TemplateScene.Navigation.BasicNavi.toMenuUiModel(),
                TemplateScene.Navigation.CategoryNavi.toMenuUiModel(),
                TemplateScene.Navigation.TextNavi.toMenuUiModel(),
                TemplateScene.Navigation.SearchNavi.toMenuUiModel(),
            )
        ),
        MenuUiModel.GroupMenu(
            groupName = "Popup",
            sceneMenus = persistentListOf(
                TemplateScene.Popup.CenterPopup.toMenuUiModel(),
                TemplateScene.Popup.BottomPopup.toMenuUiModel(),
                TemplateScene.Popup.ListPopup.toMenuUiModel(),
                TemplateScene.Popup.MiniListPopup.toMenuUiModel(),
                TemplateScene.Popup.IconPopup.toMenuUiModel(),
                TemplateScene.Popup.ToastPopup.toMenuUiModel(),
            )
        ),
        MenuUiModel.GroupMenu(
            groupName = "Profile",
            sceneMenus = persistentListOf(
                TemplateScene.Profile.BasicProfile.toMenuUiModel(),
                TemplateScene.Profile.SecondProfile.toMenuUiModel(),
                TemplateScene.Profile.SimpleProfile.toMenuUiModel(),
                TemplateScene.Profile.MiniProfile.toMenuUiModel(),
            )
        ),
        TemplateScene.RadioLabel.toMenuUiModel(),
    )
)
