package com.shopl.sdg.component.tab.box

import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class SDGBoxTabOptionTest {
    @Test
    fun optionRequiresItsTabCount() {
        val item = SDGBoxTabItem(
            state = SDGBoxTabItemState.Unselected,
            label = "Label",
            showTwoDepth = SDGBoxTabItem.ShowTwoDepth.False
        )

        assertEquals(2, SDGBoxTabOption.TwoOption(List(2) { item }).tabs.size)
        assertEquals(3, SDGBoxTabOption.ThreeOption(List(3) { item }).tabs.size)
        assertThrows(IllegalArgumentException::class.java) {
            SDGBoxTabOption.TwoOption(listOf(item))
        }
        assertThrows(IllegalArgumentException::class.java) {
            SDGBoxTabOption.ThreeOption(List(2) { item })
        }
    }
}
