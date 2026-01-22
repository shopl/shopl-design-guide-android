package com.shopl.sdg.scene

import androidx.compose.runtime.Stable
import kotlinx.collections.immutable.persistentListOf

/**
 * SDG - Component
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=6707-15003&m=dev">Figma</a>
 */
@Stable
internal sealed class ComponentScene(
    override val displayLabel: String,
    override val implemented: Boolean,
) : SDGScene(displayLabel, implemented) {

    data object Avatar : ComponentScene(
        displayLabel = "Avatar",
        implemented = false
    )

    data object AttachmentList : ComponentScene(
        displayLabel = "Attachment List",
        implemented = false
    )

    @Stable
    sealed class Badge(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : ComponentScene(displayLabel, implemented) {
        data object CapsuleBadge : Badge(
            displayLabel = "Capsule Badge",
            implemented = false
        )

        data object BoxBadge : Badge(
            displayLabel = "Box Badge",
            implemented = false
        )
    }

    @Stable
    sealed class Button(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : ComponentScene(displayLabel, implemented) {
        data object BottomButton : Button(
            displayLabel = "Bottom Button",
            implemented = true
        )

        data object BoxButton : Button(
            displayLabel = "Box Button",
            implemented = true
        )

        data object CapsuleButton : Button(
            displayLabel = "Capsule Button",
            implemented = true
        )

        data object FloatingButton : Button(
            displayLabel = "Floating Button",
            implemented = true
        )

        data object GhostButton : Button(
            displayLabel = "Ghost Button",
            implemented = true
        )

        data object IconButton : Button(
            displayLabel = "Icon Button",
            implemented = false
        )
    }

    data object Calendar : ComponentScene(
        displayLabel = "Calendar",
        implemented = false
    )

    data object Checkbox : ComponentScene(
        displayLabel = "Checkbox",
        implemented = false
    )

    data object CheckOption : ComponentScene(
        displayLabel = "Check Option",
        implemented = false
    )

    data object Dropdown : ComponentScene(
        displayLabel = "Dropdown",
        implemented = false
    )

    @Stable
    sealed class EmptyIcon(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : ComponentScene(displayLabel, implemented) {
        data object BasicEmptyIcon : EmptyIcon(
            displayLabel = "Basic Empty Icon",
            implemented = false
        )

        data object ContentsEmptyIcon : EmptyIcon(
            displayLabel = "Contents Empty Icon",
            implemented = false
        )
    }

    @Stable
    sealed class Indicator(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : ComponentScene(displayLabel, implemented) {
        data object TextIndicator : Indicator(
            displayLabel = "Text Indicator",
            implemented = false
        )

        data object NumberIndicator : Indicator(
            displayLabel = "Number Indicator",
            implemented = false
        )

        data object DotIndicator : Indicator(
            displayLabel = "Dot Indicator",
            implemented = false
        )
    }

    data object IconLabel : ComponentScene(
        displayLabel = "Icon Label",
        implemented = false
    )

    data object NumberPicker : ComponentScene(
        displayLabel = "Number Picker",
        implemented = false
    )

    @Stable
    sealed class Progress(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : ComponentScene(displayLabel, implemented) {
        data object SystemProgress : Progress(
            displayLabel = "System Progress",
            implemented = false
        )

        data object CircularProgress : Progress(
            displayLabel = "Circular Progress",
            implemented = false
        )

        data object DotProgress : Progress(
            displayLabel = "Dot Progress",
            implemented = false
        )

        data object LinearProgress : Progress(
            displayLabel = "Linear Progress",
            implemented = false
        )
    }

    data object Radio : ComponentScene(
        displayLabel = "Radio",
        implemented = false
    )

    @Stable
    sealed class SearchBar(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : ComponentScene(displayLabel, implemented) {
        data object CapsuleSearch : SearchBar(
            displayLabel = "Capsule Search",
            implemented = false
        )

        data object BoxSearch : SearchBar(
            displayLabel = "Box Search",
            implemented = false
        )

        data object CategorySearch : SearchBar(
            displayLabel = "Category Search",
            implemented = false
        )
    }

    data object Segment : ComponentScene(
        displayLabel = "Segment",
        implemented = false
    )

    data object SelectInput : ComponentScene(
        displayLabel = "Select Input",
        implemented = false
    )

    @Stable
    sealed class Tab(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : ComponentScene(displayLabel, implemented) {
        data object ScrollTab : Tab(
            displayLabel = "Scroll Tab",
            implemented = false
        )

        data object FixedTab : Tab(
            displayLabel = "Fixed Tab",
            implemented = false
        )

        data object BoxTab : Tab(
            displayLabel = "Box Tab",
            implemented = false
        )

        data object IconTab : Tab(
            displayLabel = "Icon Tab",
            implemented = false
        )
    }

    @Stable
    sealed class TextInput(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : ComponentScene(displayLabel, implemented) {
        data object SimpleTextInput : TextInput(
            displayLabel = "Simple Text Input",
            implemented = false
        )

        data object FixedTextInput : TextInput(
            displayLabel = "Fixed Text Input",
            implemented = false
        )

        data object UnderlineInput : TextInput(
            displayLabel = "Underline Input",
            implemented = false
        )

        data object LoginInput : TextInput(
            displayLabel = "Login Input",
            implemented = false
        )
    }

    data object Thumbnails : ComponentScene(
        displayLabel = "Thumbnails",
        implemented = false
    )

    data object TimePicker : ComponentScene(
        displayLabel = "Time Picker",
        implemented = false
    )

    data object TimeSelectInput : ComponentScene(
        displayLabel = "Time Select Input",
        implemented = false
    )

    data object Toggle : ComponentScene(
        displayLabel = "Toggle",
        implemented = false
    )

    data object Tooltip : ComponentScene(
        displayLabel = "Tooltip",
        implemented = false
    )
}

internal val componentScenes = persistentListOf(
    ComponentScene.Avatar,
    ComponentScene.AttachmentList,
    ComponentScene.Badge.CapsuleBadge,
    ComponentScene.Badge.BoxBadge,
    ComponentScene.Button.BottomButton,
    ComponentScene.Button.BoxButton,
    ComponentScene.Button.CapsuleButton,
    ComponentScene.Button.FloatingButton,
    ComponentScene.Button.GhostButton,
    ComponentScene.Button.IconButton,
    ComponentScene.Calendar,
    ComponentScene.Checkbox,
    ComponentScene.CheckOption,
    ComponentScene.Dropdown,
    ComponentScene.EmptyIcon.BasicEmptyIcon,
    ComponentScene.EmptyIcon.ContentsEmptyIcon,
    ComponentScene.Indicator.TextIndicator,
    ComponentScene.Indicator.NumberIndicator,
    ComponentScene.Indicator.DotIndicator,
    ComponentScene.IconLabel,
    ComponentScene.NumberPicker,
    ComponentScene.Progress.SystemProgress,
    ComponentScene.Progress.CircularProgress,
    ComponentScene.Progress.DotProgress,
    ComponentScene.Progress.LinearProgress,
    ComponentScene.Radio,
    ComponentScene.SearchBar.CapsuleSearch,
    ComponentScene.SearchBar.BoxSearch,
    ComponentScene.SearchBar.CategorySearch,
    ComponentScene.Segment,
    ComponentScene.SelectInput,
    ComponentScene.Tab.ScrollTab,
    ComponentScene.Tab.FixedTab,
    ComponentScene.Tab.BoxTab,
    ComponentScene.Tab.IconTab,
    ComponentScene.TextInput.SimpleTextInput,
    ComponentScene.TextInput.FixedTextInput,
    ComponentScene.TextInput.UnderlineInput,
    ComponentScene.TextInput.LoginInput,
    ComponentScene.Thumbnails,
    ComponentScene.TimePicker,
    ComponentScene.TimeSelectInput,
    ComponentScene.Toggle,
    ComponentScene.Tooltip,
)
