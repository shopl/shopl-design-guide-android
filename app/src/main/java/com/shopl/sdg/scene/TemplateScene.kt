package com.shopl.sdg.scene

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable

/**
 * SDG - Template
 *
 * @see <a href="https://www.figma.com/design/qWVshatQ9eqoIn4fdEZqWy/SDG?node-id=16930-2151&m=dev">Figma</a>
 */
@Stable
internal sealed class TemplateScene(
    override val displayLabel: String,
    override val implemented: Boolean,
) : SDGScene(displayLabel, implemented) {

    data object CalendarAndTime : TemplateScene(
        displayLabel = "Calendar and Time",
        implemented = false
    ) {
        override val isDarkIcon: Boolean = true

        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    data object CheckboxLabel : TemplateScene(
        displayLabel = "Checkbox Label",
        implemented = false
    ) {
        override val isDarkIcon: Boolean = true

        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    data object CheckOptionLabel : TemplateScene(
        displayLabel = "Check Option Label",
        implemented = false
    ) {
        override val isDarkIcon: Boolean = true

        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    data object EmptyImg : TemplateScene(
        displayLabel = "Empty Img",
        implemented = false
    ) {
        override val isDarkIcon: Boolean = true

        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    @Stable
    sealed class FilterChip(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : TemplateScene(displayLabel, implemented) {
        data object NaviFilterChip : FilterChip(
            displayLabel = "Navi Filter Chip",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object BodyFilterChip : FilterChip(
            displayLabel = "Body Filter Chip",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }
    }

    @Stable
    sealed class Form(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : TemplateScene(displayLabel, implemented) {
        data object DropdownForm : Form(
            displayLabel = "Dropdown Form",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object SelectForm : Form(
            displayLabel = "Select Form",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object SimpleTextForm : Form(
            displayLabel = "Simple Text Form",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object FixedTextForm : Form(
            displayLabel = "Fixed Text Form",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object TimeSelectForm : Form(
            displayLabel = "Time Select Form",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }
    }

    @Stable
    sealed class FoundationList(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : TemplateScene(displayLabel, implemented) {
        data object UserList : FoundationList(
            displayLabel = "User List",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object WorkplaceList : FoundationList(
            displayLabel = "Workplace List",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object GroupAndPositionList : FoundationList(
            displayLabel = "Group and Position List",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }
    }

    data object History : TemplateScene(
        displayLabel = "History",
        implemented = false
    ) {
        override val isDarkIcon: Boolean = true

        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    data object HistoryMini : TemplateScene(
        displayLabel = "History Mini",
        implemented = false
    ) {
        override val isDarkIcon: Boolean = true

        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    data object ListHeader : TemplateScene(
        displayLabel = "List Header",
        implemented = false
    ) {
        override val isDarkIcon: Boolean = true

        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    data object MultiCalendar : TemplateScene(
        displayLabel = "Multi Calendar",
        implemented = false
    ) {
        override val isDarkIcon: Boolean = true

        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    data object MultiTimePicker : TemplateScene(
        displayLabel = "Multi Time Picker",
        implemented = false
    ) {
        override val isDarkIcon: Boolean = true

        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    @Stable
    sealed class Navigation(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : TemplateScene(displayLabel, implemented) {
        data object BasicNavi : Navigation(
            displayLabel = "Basic Navi",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object CategoryNavi : Navigation(
            displayLabel = "Category Navi",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object TextNavi : Navigation(
            displayLabel = "Text Navi",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object SearchNavi : Navigation(
            displayLabel = "Search Navi",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }
    }

    @Stable
    sealed class Popup(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : TemplateScene(displayLabel, implemented) {
        data object CenterPopup : Popup(
            displayLabel = "Center Popup",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object BottomPopup : Popup(
            displayLabel = "Bottom Popup",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object ListPopup : Popup(
            displayLabel = "List Popup",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object MiniListPopup : Popup(
            displayLabel = "Mini List Popup",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object IconPopup : Popup(
            displayLabel = "Icon Popup",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object ToastPopup : Popup(
            displayLabel = "Toast Popup",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }
    }

    @Stable
    sealed class Profile(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : TemplateScene(displayLabel, implemented) {
        data object BasicProfile : Profile(
            displayLabel = "Basic Profile",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object SecondProfile : Profile(
            displayLabel = "Second Profile",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object SimpleProfile : Profile(
            displayLabel = "Simple Profile",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object MiniProfile : Profile(
            displayLabel = "Mini Profile",
            implemented = false
        ) {
            override val isDarkIcon: Boolean = true

            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }
    }

    data object RadioLabel : TemplateScene(
        displayLabel = "Radio Label",
        implemented = false
    ) {
        override val isDarkIcon: Boolean = true

        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }
}
