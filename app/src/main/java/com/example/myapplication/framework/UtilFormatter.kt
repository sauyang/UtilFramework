package com.example.myapplication.framework

import android.text.TextUtils
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.*

class UtilFormatter {
    companion object{
        private const val DEFAULT_1_DECIMAL_FORMAT = "###,###,###,###,##0.0"
        private const val DEFAULT_DECIMAL_FORMAT = "###,###,###,###,##0.00"
        private const val DEFAULT_4_DECIMAL_FORMAT = "###,###,###,###,##0.0000"
        private const val DEFAULT_0_DECIMAL_FORMAT = "###,###,###,###,##0"
        private val locale = Locale("en")
        private val symbols = DecimalFormatSymbols(locale)

        /**
         * Format currency to ###,###,###,###,##0.00
         * if amount is 0, it will default to 0.00
         *
         * @param amount non-formatted amount
         * @return formatted amount
         */
        fun format(amount: String): String {
            return format(amount, false)
        }

        fun formatSpecial(amount: Double?): String? {
            val formattedAmount = format(amount, false)
            return if (null != formattedAmount && formattedAmount.equals("0", ignoreCase = true) ||
                null != formattedAmount && formattedAmount.equals("0.00", ignoreCase = true)
            ) {
                "-"
            } else formattedAmount
        }

        /**
         * Format currency to ###,###,###,###,##0
         * if amount is 0, it will default to 0
         *
         * @param amount non-formatted amount
         * @return formatted amount
         */
        fun format0Decimal(amount: String): String? {
            if (TextUtils.isEmpty(amount)) {
                return amount
            }
            /**
             * Important NOTICE
             */
            /** Use Big decimal instead because double amount will round up if digit more than 15 digits */
            val bigDecimalAmount: BigDecimal
            bigDecimalAmount = try {
                BigDecimal(amount.replace(",".toRegex(), ""))
            } catch (e: Exception) {
                e.printStackTrace()
                return amount
            }
            val formatter =
                DecimalFormat(DEFAULT_0_DECIMAL_FORMAT, symbols)
            return formatter.format(bigDecimalAmount)
        }

        /**
         * Format currency to ###,###,###,###,##0.00
         * if amount is 0, it will default to 0.00
         *
         * @param amount non-formatted amount
         * @return formatted amount
         */
        fun format(amount: String, is4Decimal: Boolean): String {
            if (TextUtils.isEmpty(amount)) {
                return amount
            }
            /**
             * Important NOTICE
             */
            /** Use Big decimal instead because double amount will round up if digit more than 15 digits */
            val bigDecimalAmount: BigDecimal
            bigDecimalAmount = try {
                BigDecimal(amount.replace(",".toRegex(), ""))
            } catch (e: Exception) {
                e.printStackTrace()
                return amount
            }
            val formatter = DecimalFormat(
                if (is4Decimal) DEFAULT_4_DECIMAL_FORMAT else DEFAULT_DECIMAL_FORMAT,
                symbols
            )
            return formatter.format(bigDecimalAmount)
        }

        /**
         * Format currency to ###,###,###,###,##0.00
         * if amount is 0, it will default to 0.00
         *
         * @param amount non-formatted amount
         * @return formatted amount
         */
        fun format1Decimal(amount: String): String? {
            if (TextUtils.isEmpty(amount)) {
                return amount
            }
            /**
             * Important NOTICE
             */
            /** Use Big decimal instead because double amount will round up if digit more than 15 digits */
            val bigDecimalAmount: BigDecimal
            bigDecimalAmount = try {
                BigDecimal(amount.replace(",".toRegex(), ""))
            } catch (e: Exception) {
                e.printStackTrace()
                return amount
            }
            val formatter =
                DecimalFormat(DEFAULT_1_DECIMAL_FORMAT, symbols)
            return formatter.format(bigDecimalAmount)
        }

        /**
         * Format double amount to Amount Currency type
         *
         * @param amount double amount
         * @return formatted amount
         */
        fun format(amount: Double?): String? {
            return format(amount, false)
        }

        /**
         * Format double amount to Amount Currency type
         *
         * @param amount     double amount
         * @param is4Decimal if is 4 decimal
         * @return formatted amount
         */
        fun format(amount: Double?, is4Decimal: Boolean): String? {
            val formatter = DecimalFormat(
                if (is4Decimal) DEFAULT_4_DECIMAL_FORMAT else DEFAULT_DECIMAL_FORMAT,
                symbols
            )
            return formatter.format(amount)
        }


        /**
         * Format double amount to Amount Currency type
         *
         * @param amount     String amount
         * @return formatted amount
         */
        fun formatWithoutRoundUp(
            amount: String,
            is4Decimal: Boolean
        ): String? {
            if (TextUtils.isEmpty(amount)) {
                return amount
            }
            val f1 = amount.toDouble()
            val formatter = DecimalFormat(
                if (is4Decimal) DEFAULT_4_DECIMAL_FORMAT else DEFAULT_DECIMAL_FORMAT,
                symbols
            )
            formatter.roundingMode = RoundingMode.DOWN // Note this extra step
            return formatter.format(f1)
        }


        fun formatRemoveComma(amount: String): String? {
            return amount.replace(",".toRegex(), "")
        }

        fun formatIfNegativeShowCR(amount: String): String? {
            /** Only for Credit Card amount display for Level 2 & 3 */
            if (TextUtils.isEmpty(amount)) {
                return amount
            }
            /** Check if negative value */
            return if ("-" == amount.substring(0, 1)) {
                "CR $amount"
            } else {
                amount
            }
        }

        fun formatInterestRate(interestAmount: String): String? {
            var formattedInterestAmount = interestAmount
            if (TextUtils.isEmpty(formattedInterestAmount)) {
                return formattedInterestAmount
            }
            /** format to 2 decimal places  */
            formattedInterestAmount = format(formattedInterestAmount)
            /** replace dot with comma  */
            formattedInterestAmount = formattedInterestAmount.replace(".", ",")
            /** Append Percentage at the back */
            formattedInterestAmount += "%"
            return formattedInterestAmount
        }

        private const val KEY_ASK = "*"

        /*0 is first char */
        fun phoneNumber(phone: String, from: Int, to: Int): String? {
            return phoneNumber(phone, from, to, KEY_ASK)
        }

        fun phoneNumber(
            phone: String,
            from: Int,
            to: Int,
            maskChar: String?
        ): String? {
            if (TextUtils.isEmpty(phone)) {
                return ""
            }
            val maskLength = to - from
            if (from + to + 1 > phone.length) {
                return phone
            }
            val stringBuilder = StringBuilder()
            stringBuilder.append(phone.substring(0, from))
            for (i in 0 until maskLength) {
                stringBuilder.append(maskChar)
            }
            stringBuilder.append(phone.substring(to))
            return stringBuilder.toString()
        }

        fun maskName(ktpLangkapName: String): String? {
            val starName = "*"
            val maskName = StringBuilder()
            if (TextUtils.isEmpty(ktpLangkapName)) {
                return ""
            }
            val listName = ktpLangkapName.split(" ").toTypedArray()
            for (i in listName.indices) {
                listName[i] = listName[i]
                val singleName = listName[i][0]
                val startString = StringBuilder()
                for (i1 in 1 until listName[i].length) {
                    startString.append(starName)
                }
                listName[i] = singleName.toString() + startString.toString()
            }
            for (s in listName) {
                maskName.append(" ").append(s)
            }
            return maskName.toString()
        }

        fun removeNonNumberFromString(string: String): String {
            return string.replace("[^\\d+]".toRegex(), "")
        }

        fun NPWPFormat(input: String): String? {
            var input = input
            if (input.length <= 0) {
                return ""
            }
            input = removeNonNumberFromString(input)
            val Regex_NPWP = "##.###.###.#-###.###"
            var j = 0
            val result = java.lang.StringBuilder()
            val formatArray = Regex_NPWP.toCharArray()
            val inputArray = input.toCharArray()
            for (c in formatArray) {
                if (c.toString() + "" != "#") {
                    result.append(c)
                } else {
                    result.append(inputArray[j])
                    j++
                    if (j == inputArray.size) {
                        break
                    }
                }
            }
            return result.toString()
        }
    }
}