package com.shopl.sdg.navigation

import androidx.compose.runtime.Stable

/**
 * SDG - Component
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=6707-15003&m=dev">Figma</a>
 */
@Stable
sealed class ComponentDestination(
    override val displayLabel: String,
    override val implemented: Boolean,
) : SDGDestination(displayLabel, implemented) {

    data object Avatar : ComponentDestination(
        displayLabel = "Avatar",
        implemented = false
    )

    data object AttachmentList : ComponentDestination(
        displayLabel = "Attachment List",
        implemented = false
    )

    @Stable
    sealed class Badge(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : ComponentDestination(displayLabel, implemented) {
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
    ) : ComponentDestination(displayLabel, implemented) {
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

    data object Calendar : ComponentDestination(
        displayLabel = "Calendar",
        implemented = false
    )

    data object Checkbox : ComponentDestination(
        displayLabel = "Checkbox",
        implemented = false
    )

    data object CheckOption : ComponentDestination(
        displayLabel = "Check Option",
        implemented = false
    )

    data object Dropdown : ComponentDestination(
        displayLabel = "Dropdown",
        implemented = false
    )

    @Stable
    sealed class EmptyIcon(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : ComponentDestination(displayLabel, implemented) {
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
    ) : ComponentDestination(displayLabel, implemented) {
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

    data object IconLabel : ComponentDestination(
        displayLabel = "Icon Label",
        implemented = false
    )

    data object NumberPicker : ComponentDestination(
        displayLabel = "Number Picker",
        implemented = false
    )

    @Stable
    sealed class Progress(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : ComponentDestination(displayLabel, implemented) {
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

    data object Radio : ComponentDestination(
        displayLabel = "Radio",
        implemented = false
    )

    @Stable
    sealed class SearchBar(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : ComponentDestination(displayLabel, implemented) {
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

    data object Segment : ComponentDestination(
        displayLabel = "Segment",
        implemented = false
    )

    data object SelectInput : ComponentDestination(
        displayLabel = "Select Input",
        implemented = false
    )

    @Stable
    sealed class Tab(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : ComponentDestination(displayLabel, implemented) {
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
    ) : ComponentDestination(displayLabel, implemented) {
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

    data object Thumbnails : ComponentDestination(
        displayLabel = "Thumbnails",
        implemented = false
    )

    data object TimePicker : ComponentDestination(
        displayLabel = "Time Picker",
        implemented = false
    )

    data object TimeSelectInput : ComponentDestination(
        displayLabel = "Time Select Input",
        implemented = false
    )

    data object Toggle : ComponentDestination(
        displayLabel = "Toggle",
        implemented = false
    )

    data object Tooltip : ComponentDestination(
        displayLabel = "Tooltip",
        implemented = false
    )
}
