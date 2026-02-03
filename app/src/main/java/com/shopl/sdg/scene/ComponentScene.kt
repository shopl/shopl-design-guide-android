package com.shopl.sdg.scene

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.shopl.sdg.ui.screen.component.button.BottomButtonScreen
import com.shopl.sdg.ui.screen.component.button.BoxButtonScreen
import com.shopl.sdg.ui.screen.component.button.CapsuleButtonScreen
import com.shopl.sdg.ui.screen.component.button.FloatingButtonScreen
import com.shopl.sdg.ui.screen.component.button.GhostButtonScreen

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
    ) {
        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    data object AttachmentList : ComponentScene(
        displayLabel = "Attachment List",
        implemented = false
    ) {
        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    @Stable
    sealed class Badge(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : ComponentScene(displayLabel, implemented) {
        data object CapsuleBadge : Badge(
            displayLabel = "Capsule Badge",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object BoxBadge : Badge(
            displayLabel = "Box Badge",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }
    }

    @Stable
    sealed class Button(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : ComponentScene(displayLabel, implemented) {
        data object BottomButton : Button(
            displayLabel = "Bottom Button",
            implemented = true
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                BottomButtonScreen(
                    onClickBack = backToScene,
                    onClickMenu = {
                        moveToScene(Menu)
                    }
                )
            }
        }

        data object BoxButton : Button(
            displayLabel = "Box Button",
            implemented = true
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                BoxButtonScreen(
                    onClickBack = backToScene,
                    onClickMenu = {
                        moveToScene(Menu)
                    }
                )
            }
        }

        data object CapsuleButton : Button(
            displayLabel = "Capsule Button",
            implemented = true
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                CapsuleButtonScreen(
                    onClickBack = backToScene,
                    onClickMenu = {
                        moveToScene(Menu)
                    }
                )
            }
        }

        data object FloatingButton : Button(
            displayLabel = "Floating Button",
            implemented = true
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                FloatingButtonScreen(
                    onClickBack = backToScene,
                    onClickMenu = {
                        moveToScene(Menu)
                    }
                )
            }
        }

        data object GhostButton : Button(
            displayLabel = "Ghost Button",
            implemented = true
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                GhostButtonScreen(
                    onClickBack = backToScene,
                    onClickMenu = {
                        moveToScene(Menu)
                    }
                )
            }
        }

        data object IconButton : Button(
            displayLabel = "Icon Button",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }
    }

    data object Calendar : ComponentScene(
        displayLabel = "Calendar",
        implemented = false
    ) {
        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    data object Checkbox : ComponentScene(
        displayLabel = "Checkbox",
        implemented = false
    ) {
        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    data object CheckOption : ComponentScene(
        displayLabel = "Check Option",
        implemented = false
    ) {
        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    data object Dropdown : ComponentScene(
        displayLabel = "Dropdown",
        implemented = false
    ) {
        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    @Stable
    sealed class EmptyIcon(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : ComponentScene(displayLabel, implemented) {
        data object BasicEmptyIcon : EmptyIcon(
            displayLabel = "Basic Empty Icon",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object ContentsEmptyIcon : EmptyIcon(
            displayLabel = "Contents Empty Icon",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }
    }

    @Stable
    sealed class Indicator(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : ComponentScene(displayLabel, implemented) {
        data object TextIndicator : Indicator(
            displayLabel = "Text Indicator",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object NumberIndicator : Indicator(
            displayLabel = "Number Indicator",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object DotIndicator : Indicator(
            displayLabel = "Dot Indicator",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }
    }

    data object IconLabel : ComponentScene(
        displayLabel = "Icon Label",
        implemented = false
    ) {
        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    data object NumberPicker : ComponentScene(
        displayLabel = "Number Picker",
        implemented = false
    ) {
        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    @Stable
    sealed class Progress(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : ComponentScene(displayLabel, implemented) {
        data object SystemProgress : Progress(
            displayLabel = "System Progress",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object CircularProgress : Progress(
            displayLabel = "Circular Progress",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object DotProgress : Progress(
            displayLabel = "Dot Progress",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object LinearProgress : Progress(
            displayLabel = "Linear Progress",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }
    }

    data object Radio : ComponentScene(
        displayLabel = "Radio",
        implemented = false
    ) {
        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    @Stable
    sealed class SearchBar(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : ComponentScene(displayLabel, implemented) {
        data object CapsuleSearch : SearchBar(
            displayLabel = "Capsule Search",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object BoxSearch : SearchBar(
            displayLabel = "Box Search",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object CategorySearch : SearchBar(
            displayLabel = "Category Search",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }
    }

    data object Segment : ComponentScene(
        displayLabel = "Segment",
        implemented = false
    ) {
        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    data object SelectInput : ComponentScene(
        displayLabel = "Select Input",
        implemented = false
    ) {
        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    @Stable
    sealed class Tab(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : ComponentScene(displayLabel, implemented) {
        data object ScrollTab : Tab(
            displayLabel = "Scroll Tab",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object FixedTab : Tab(
            displayLabel = "Fixed Tab",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object BoxTab : Tab(
            displayLabel = "Box Tab",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object IconTab : Tab(
            displayLabel = "Icon Tab",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }
    }

    @Stable
    sealed class TextInput(
        override val displayLabel: String,
        override val implemented: Boolean,
    ) : ComponentScene(displayLabel, implemented) {
        data object SimpleTextInput : TextInput(
            displayLabel = "Simple Text Input",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object FixedTextInput : TextInput(
            displayLabel = "Fixed Text Input",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object UnderlineInput : TextInput(
            displayLabel = "Underline Input",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }

        data object LoginInput : TextInput(
            displayLabel = "Login Input",
            implemented = false
        ) {
            @Composable
            override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
                throw IllegalStateException("Not implemented")
            }
        }
    }

    data object Thumbnails : ComponentScene(
        displayLabel = "Thumbnails",
        implemented = false
    ) {
        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    data object TimePicker : ComponentScene(
        displayLabel = "Time Picker",
        implemented = false
    ) {
        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    data object TimeSelectInput : ComponentScene(
        displayLabel = "Time Select Input",
        implemented = false
    ) {
        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    data object Toggle : ComponentScene(
        displayLabel = "Toggle",
        implemented = false
    ) {
        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }

    data object Tooltip : ComponentScene(
        displayLabel = "Tooltip",
        implemented = false
    ) {
        @Composable
        override fun Screen(moveToScene: (SDGScene) -> Unit, backToScene: () -> Unit) {
            throw IllegalStateException("Not implemented")
        }
    }
}
