package com.tzeentch.energy_saver.helpers

import java.math.RoundingMode
import java.text.DecimalFormat

object RoundFloor {
    fun roundOffDecimal(number: Float): Float {
        val df = DecimalFormat("#,##")
        df.roundingMode = RoundingMode.FLOOR
        return df.format(number).toFloat()
    }
}