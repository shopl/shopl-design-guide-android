package com.shopl.sdg.navigation

import androidx.compose.runtime.Stable
import kotlinx.serialization.Serializable

/**
 * SDG - Component
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=6707-15003&m=dev">Figma</a>
 */
@Stable
sealed interface ComponentDestination {
    @Serializable
    data object Avatar : ComponentDestination

    @Serializable
    data object AttachmentList : ComponentDestination

    @Stable
    sealed interface Badge : ComponentDestination {
        @Serializable
        data object CapsuleBadge : Badge

        @Serializable
        data object BoxBadge : Badge
    }

    @Stable
    sealed interface Button : ComponentDestination {
        @Serializable
        data object BottomButton : Button

        @Serializable
        data object BoxButton : Button

        @Serializable
        data object CapsuleButton : Button

        @Serializable
        data object FloatingButton : Button

        @Serializable
        data object GhostButton : Button

        @Serializable
        data object IconButton : Button
    }

    @Serializable
    data object Calendar : ComponentDestination

    @Serializable
    data object Checkbox : ComponentDestination

    @Serializable
    data object CheckOption : ComponentDestination

    @Serializable
    data object Dropdown : ComponentDestination

    @Stable
    sealed interface EmptyIcon : ComponentDestination {
        @Serializable
        data object BasicEmptyIcon : EmptyIcon

        @Serializable
        data object ContentsEmptyIcon : EmptyIcon
    }

    @Stable
    sealed interface Indicator : ComponentDestination {
        @Serializable
        data object TextIndicator : Indicator

        @Serializable
        data object NumberIndicator : Indicator

        @Serializable
        data object DotIndicator : Indicator
    }

    @Serializable
    data object IconLabel : ComponentDestination

    @Serializable
    data object NumberPicker : ComponentDestination

    @Stable
    sealed interface Progress : ComponentDestination {
        @Serializable
        data object SystemProgress : Progress

        @Serializable
        data object CircularProgress : Progress

        @Serializable
        data object DotProgress : Progress

        @Serializable
        data object LinearProgress : Progress
    }

    @Serializable
    data object Radio : ComponentDestination

    @Stable
    sealed interface SearchBar : ComponentDestination {
        @Serializable
        data object CapsuleSearch : SearchBar

        @Serializable
        data object BoxSearch : SearchBar

        @Serializable
        data object CategorySearch : SearchBar
    }

    @Serializable
    data object Segment : ComponentDestination

    @Serializable
    data object SelectInput : ComponentDestination

    @Stable
    sealed interface Tab : ComponentDestination {
        @Serializable
        data object ScrollTab : Tab

        @Serializable
        data object FixedTab : Tab

        @Serializable
        data object BoxTab : Tab

        @Serializable
        data object IconTab : Tab
    }

    @Stable
    sealed interface TextInput : ComponentDestination {
        @Serializable
        data object SimpleTextInput : TextInput

        @Serializable
        data object FixedTextInput : TextInput

        @Serializable
        data object UnderlineInput : TextInput

        @Serializable
        data object LoginInput : TextInput
    }

    @Serializable
    data object Thumbnails : ComponentDestination

    @Serializable
    data object TimePicker : ComponentDestination

    @Serializable
    data object TimeSelectInput : ComponentDestination

    @Serializable
    data object Toggle : ComponentDestination

    @Serializable
    data object Tooltip : ComponentDestination
}
