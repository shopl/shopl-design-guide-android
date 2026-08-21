package com.shopl.sdg.template.multi_time_picker

import org.junit.Assert.assertEquals
import org.junit.Test

class FixedBottomPopupDismissStateTest {
    @Test
    fun consumePendingAction_runsOnlyTheFirstDismissAction() {
        var invocationCount = 0
        val state = FixedBottomPopupDismissState()

        state.updateDefaultAction { invocationCount++ }
        state.requestDismiss()
        state.requestDismiss { invocationCount += 10 }
        state.consumePendingAction()
        state.consumePendingAction()

        assertEquals(1, invocationCount)
    }
}
