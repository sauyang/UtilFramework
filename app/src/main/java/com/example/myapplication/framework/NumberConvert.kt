package com.example.myapplication.framework

import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.util.*

class NumberConvert {

    companion object {

        fun formatAmountToCurrency(value: String?): String? {
            return if (value == null || value.isEmpty()) value else doubleToString(
                stringToDouble(
                    value
                )
            )
        }

        fun stringToDouble(value: String?): Double {
            return if (value == null || value.isEmpty()) 0.0 else stringToDouble(
                Locale.ENGLISH,
                value
            )
        }

        fun doubleToString(value: Double): String? {
            return doubleToString(Locale.ENGLISH, value, "#,##0.00")
        }

        fun doubleToString(value: Double, applyPattern: String?): String? {
            return doubleToString(Locale.ENGLISH, value, applyPattern)
        }

        fun formatText(value: String?, applyPattern: String?): String? {
            val numberFormat =
                NumberFormat.getNumberInstance(Locale.ENGLISH)
            val decimalFormat = numberFormat as DecimalFormat
            decimalFormat.applyPattern(applyPattern)
            return numberFormat.format(stringToDouble(value))
        }

        fun doubleToString(
            currentLocale: Locale?,
            value: Double,
            applyPattern: String?
        ): String? {
            val numberFormat =
                NumberFormat.getNumberInstance(currentLocale)
            val decimalFormat = numberFormat as DecimalFormat
            decimalFormat.applyPattern(applyPattern)
            return numberFormat.format(value)
        }

        private fun stringToDouble(
            currentLocale: Locale,
            value: String
        ): Double {
            val numberFormat =
                NumberFormat.getNumberInstance(currentLocale)
            try {
                return numberFormat.parse(value).toDouble()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return 0.0
        }

    }


}