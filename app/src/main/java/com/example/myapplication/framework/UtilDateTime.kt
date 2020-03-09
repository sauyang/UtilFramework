package com.example.myapplication.framework

import java.text.DateFormatSymbols
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class UtilDateTime {

    companion object Formatter {

        val DATE_FORMATTER_TYPE_1 = "dd MMM yyyy"
        val DATE_FORMATTER_TYPE_2 = "MMM dd, yyyy hh:mm:ss a"
        val DATE_FORMATTER_TYPE_3 = "yyyy-MM-dd HH:mm:ss"
        val DATE_FORMATTER_TYPE_4 = "dd MMMM yyyy"
        val DATE_FORMATTER_TYPE_5 = "dd MMMM yyyy HH:mm"
        val DATE_FORMATTER_TYPE_6 = "dd-MM-yyyy HH:mm"
        val DATE_FORMATTER_TYPE_7 = "dd-MM-yyyy"
        val DATE_FORMATTER_TYPE_8 = "yyyy-MM-dd"
        val DATE_FORMATTER_TYPE_9 = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
        val DATE_FORMATTER_TYPE_10 = "dd/MM/yyyy"
        val DATE_FORMATTER_TYPE_11 = "EEEE, dd MMM yyyy HH:mm:ss"
        val DATE_FORMATTER_TYPE_12 = "EEEE, dd MMM yyyy"
        val DATE_FORMATTER_TYPE_13 = "dd/MM/yyyy hh:mm a"
        val DATE_FORMATTER_TYPE_14 = "dd/MM/yyyy, hh:mma"
        val DATE_FORMATTER_TYPE_15 = "dd MMM, yyyy hh:mm:ss a"
        val DATE_FORMATTER_TYPE_16 = "dd MMM yyyy HH:mm:ss"


        /**
         * @param date             new Date()
         * @param dateResultFormat example : yyyy-MM-dd'T'HH:mm:ss'Z'
         * @return 2017-10-27T19:01:22Z
         */
        fun toFormat(date: Date?, dateResultFormat: String?): String {
            return SimpleDateFormat(dateResultFormat, Locale.US).format(date)
        }
        /**
         * @param dateString "2010-10-15T09:27:37Z"
         * @param format     yyyy-MM-dd'T'HH:mm:ss'Z'
         * @return new Date()
         */
        /**
         * @param dateString "2010-10-15T09:27:37Z"
         * @param format     yyyy-MM-dd'T'HH:mm:ss'Z'
         * @return new Date()
         */
        @JvmOverloads
        fun toDate(
            dateString: String?,
            format: String?,
            isReturnNull: Boolean = false
        ): Date? {
            val simpleDateFormat =
                SimpleDateFormat(format, Locale.US)
            try {
                return simpleDateFormat.parse(dateString)
            } catch (e: ParseException) {
                e.printStackTrace()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return if (isReturnNull) null else Date()
        }

        /**
         * @param dateString
         * @param format
         * @param locale
         * @return
         */
        fun toDateWithLocale(
            dateString: String?,
            format: String?,
            locale: Locale?
        ): String {
            val milliSeconds = toDate(dateString, DATE_FORMATTER_TYPE_2)!!.time
            val date = Date(milliSeconds)
            val simpleDateFormat =
                SimpleDateFormat(format, locale)
            try {
                return simpleDateFormat.format(date)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return Date().toString()
        }

        fun toDateWithLocale(
            dateString: String?,
            formatTo: String?,
            formatFrom: String?,
            localeString: String?
        ): String {
            val locale = Locale(localeString)
            val date = fromValueToValue(dateString, formatTo, formatFrom)
            return toDateWithLocale(date, formatFrom, locale)
        }

        /**
         * @param value      2010-10-15T09:27:37Z
         * @param formatFrom yyyy-MM-dd'T'HH:mm:ss'Z'
         * @param formatTo   dd MMM yyyy
         * @return 15 Oct 2010
         */
        fun fromValueToValue(
            value: String?,
            formatFrom: String?,
            formatTo: String?
        ): String? {
            if (value == null) {
                return null
            }
            val simpleDateFormat =
                SimpleDateFormat(formatFrom, Locale.US)
            try {
                return toFormat(simpleDateFormat.parse(value), formatTo)
            } catch (ignored: ParseException) {
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return value
        }

        fun fromMilliSecondToValue(
            milli: String?,
            formatTo: String?
        ): String? {
            if (milli == null) {
                return null
            }
            val milliSeconds = milli.toLong()
            val date = Date(milliSeconds)
            return toFormat(date, formatTo)
        }

        fun isToday(date1: Date?, date2: Date?): Boolean {
            val calendar1 = Calendar.getInstance()
            calendar1.time = date1
            val calendar2 = Calendar.getInstance()
            calendar2.time = date2
            val year1 = calendar1[Calendar.YEAR]
            val dayOfYear1 = calendar1[Calendar.DAY_OF_YEAR]
            val year2 = calendar2[Calendar.YEAR]
            val dayOfYear2 = calendar2[Calendar.DAY_OF_YEAR]
            return year1 == year2 && dayOfYear1 == dayOfYear2
        }

        /**
         * @param _date
         * @return integer month
         */
        fun getDateMonth(_date: Date?): Int {
            return if (_date != null) {
                val cal = Calendar.getInstance()
                cal.time = _date
                cal[Calendar.MONTH] + 1
            } else 0
        }

        /**
         * @param _date
         * @return integer year
         */
        fun getDateYear(_date: Date?): Int {
            return if (_date != null) {
                val cal = Calendar.getInstance()
                cal.time = _date
                cal[Calendar.YEAR]
            } else 0
        }

        /**
         * @param month
         * @return month full name
         */
        fun getMonthFullName(month: Int): String {
            return DateFormatSymbols(Locale.US).months[month - 1]
        }


        /**
         * Get date with plus or minus of days
         *
         *
         * To get date 30 days after starting date
         * e.g. getDateFromStartDate(startingDate, 30)
         *
         *
         * To get date 30 days before starting date
         * e.g. getDateFromStartDate(startingDate, -30)
         *
         * @param startingDate current date
         * @param dayDifferent plus days
         * @return
         */
        fun getDatePlusDay(startingDate: Date?, dayDifferent: Int): Date {
            val calendar = Calendar.getInstance()
            calendar.time = startingDate
            calendar[Calendar.DAY_OF_YEAR] = calendar[Calendar.DAY_OF_YEAR] + dayDifferent
            return calendar.time
        }

        fun getTodayDate(format: String?): String {
            val dateFormatter =
                SimpleDateFormat(format, Locale.US)
            return dateFormatter.format(Date())
        }

        fun getDatePlusMonths(startingDate: Date?, monthDifferent: Int): Date {
            val calendar = Calendar.getInstance()
            calendar.time = startingDate
            calendar[Calendar.MONTH] = calendar[Calendar.MONTH] + monthDifferent
            return calendar.time
        }

        fun getDateCountDate(
            fromDate: Date?,
            todayDate: Date,
            monthDifferent: Int
        ): Date {
            val calendar = Calendar.getInstance()
            calendar.time = fromDate
            calendar[Calendar.MONTH] = calendar[Calendar.MONTH] + monthDifferent
            return if (calendar.time.time < todayDate.time) {
                calendar.time
            } else {
                todayDate
            }
        }

        fun getDayOfMonthSuffix(n: Int): String {
            return when (n) {
                1, 21, 31 -> "st"
                2, 22 -> "nd"
                3, 13 -> "rd"
                else -> "th"
            }
        }

        fun daysDifference(startDate: Date, endDate: Date): Int {
            val different = endDate.time - startDate.time
            val daysInMilli = 1000 * 60 * 60 * 24.toLong()
            val elapsedDays = different / daysInMilli
            return if (elapsedDays > Int.MAX_VALUE) {
                Int.MAX_VALUE
            } else {
                elapsedDays.toInt()
            }
        }

        fun getZeroTime(date: Date?): Date {
            val calDateTime = Calendar.getInstance()
            calDateTime.time = date
            calDateTime.time = date
            calDateTime[Calendar.HOUR_OF_DAY] = 0
            calDateTime[Calendar.MINUTE] = 0
            calDateTime[Calendar.SECOND] = 0
            calDateTime[Calendar.MILLISECOND] = 0
            return calDateTime.time
        }
    }

}