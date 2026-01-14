package com.shopl.sdg.navigation

import androidx.compose.runtime.Stable

/**
 * SDG - Template
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=16930-2151&m=dev">Figma</a>
 */
@Stable
sealed class TemplateDestination(
    override val displayLabel: String,
    override val implemented: Boolean,
) : SDGDestination(displayLabel, implemented) {

    data object CalendarAndTime : TemplateDestination(
        displayLabel = "Calendar and Time",
        implemented = false
    )

    data object CheckboxLabel : TemplateDestination(
        displayLabel = "Checkbox Label",
        implemented = false
    )

    data object CheckOptionLabel : TemplateDestination(
        displayLabel = "Check Option Label",
        implemented = false
    )

    data object EmptyImg : TemplateDestination(
        displayLabel = "Empty Img",
        implemented = false
    )

    @Stable
    sealed class FilterChip(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : TemplateDestination(displayLabel, implemented) {
        data object NaviFilterChip : FilterChip(
            displayLabel = "Navi Filter Chip",
            implemented = false
        )

        data object BodyFilterChip : FilterChip(
            displayLabel = "Body Filter Chip",
            implemented = false
        )
    }

    @Stable
    sealed class Form(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : TemplateDestination(displayLabel, implemented) {
        data object DropdownForm : Form(
            displayLabel = "Dropdown Form",
            implemented = false
        )

        data object SelectForm : Form(
            displayLabel = "Select Form",
            implemented = false
        )

        data object SimpleTextForm : Form(
            displayLabel = "Simple Text Form",
            implemented = false
        )

        data object FixedTextForm : Form(
            displayLabel = "Fixed Text Form",
            implemented = false
        )

        data object TimeSelectForm : Form(
            displayLabel = "Time Select Form",
            implemented = false
        )
    }

    @Stable
    sealed class FoundationList(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : TemplateDestination(displayLabel, implemented) {
        data object UserList : FoundationList(
            displayLabel = "User List",
            implemented = false
        )

        data object WorkplaceList : FoundationList(
            displayLabel = "Workplace List",
            implemented = false
        )

        data object GroupAndPositionList : FoundationList(
            displayLabel = "Group and Position List",
            implemented = false
        )
    }

    data object History : TemplateDestination(
        displayLabel = "History",
        implemented = false
    )

    data object HistoryMini : TemplateDestination(
        displayLabel = "History Mini",
        implemented = false
    )

    data object ListHeader : TemplateDestination(
        displayLabel = "List Header",
        implemented = false
    )

    data object MultiCalendar : TemplateDestination(
        displayLabel = "Multi Calendar",
        implemented = false
    )

    data object MultiTimePicker : TemplateDestination(
        displayLabel = "Multi Time Picker",
        implemented = false
    )

    @Stable
    sealed class Navigation(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : TemplateDestination(displayLabel, implemented) {
        data object BasicNavi : Navigation(
            displayLabel = "Basic Navi",
            implemented = false
        )

        data object CategoryNavi : Navigation(
            displayLabel = "Category Navi",
            implemented = false
        )

        data object TextNavi : Navigation(
            displayLabel = "Text Navi",
            implemented = false
        )

        data object SearchNavi : Navigation(
            displayLabel = "Search Navi",
            implemented = false
        )
    }

    @Stable
    sealed class Popup(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : TemplateDestination(displayLabel, implemented) {
        data object CenterPopup : Popup(
            displayLabel = "Center Popup",
            implemented = false
        )

        data object BottomPopup : Popup(
            displayLabel = "Bottom Popup",
            implemented = false
        )

        data object ListPopup : Popup(
            displayLabel = "List Popup",
            implemented = false
        )

        data object MiniListPopup : Popup(
            displayLabel = "Mini List Popup",
            implemented = false
        )

        data object IconPopup : Popup(
            displayLabel = "Icon Popup",
            implemented = false
        )

        data object ToastPopup : Popup(
            displayLabel = "Toast Popup",
            implemented = false
        )
    }

    @Stable
    sealed class Profile(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : TemplateDestination(displayLabel, implemented) {
        data object BasicProfile : Profile(
            displayLabel = "Basic Profile",
            implemented = false
        )

        data object SecondProfile : Profile(
            displayLabel = "Second Profile",
            implemented = false
        )

        data object SimpleProfile : Profile(
            displayLabel = "Simple Profile",
            implemented = false
        )

        data object MiniProfile : Profile(
            displayLabel = "Mini Profile",
            implemented = false
        )
    }

    data object RadioLabel : TemplateDestination(
        displayLabel = "Radio Label",
        implemented = false
    )
}
