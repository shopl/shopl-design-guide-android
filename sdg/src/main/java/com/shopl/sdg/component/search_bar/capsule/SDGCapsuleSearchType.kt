package com.shopl.sdg.component.search_bar.capsule

sealed interface SDGCapsuleSearchType {
    data object Solid : SDGCapsuleSearchType
    data object Line : SDGCapsuleSearchType
}
