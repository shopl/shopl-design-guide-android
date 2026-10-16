package com.shopl.sdg

import androidx.compose.ui.test.junit4.createComposeRule
import com.shopl.sdg.component.progress.linear.SDGLinearProgress
import com.shopl.sdg_common.foundation.SDGColor
import org.junit.Assert.assertThrows
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class SDGLinearProgressInvalidValueTest(private val progress: Float) {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun invalidProgressIsRejected() {
        assertThrows(IllegalArgumentException::class.java) {
            composeRule.setContent {
                SDGLinearProgress(progress = progress, label = null, labelColor = SDGColor.Neutral700)
            }
        }
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "progress={0}")
        fun invalidValues(): List<Float> = listOf(
            -0.1f,
            1.1f,
            Float.NaN,
            Float.NEGATIVE_INFINITY,
            Float.POSITIVE_INFINITY,
        )
    }
}
