package com.doug.newsapp.helpers.datehelper

import com.doug.newsapp.helpers.constants.Constants
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


/**
 * Helper date class to perform date transformations
 */
object DateHelper {


    private val dateFormat = SimpleDateFormat(Constants.DATE_FORMAT, Locale.US)
    private var convertedDate: Date? = Date()
    private val currentDate = Date().time

    /**
     * Method used to calculate the difference between NOW and another date
     * @param date Date to be parsed
     */
    @Synchronized
    fun getPassedTime(date: String): String {
        try {
            convertedDate = dateFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val hours = (currentDate - convertedDate!!.time)
        return TimeUnit.MILLISECONDS.toHours(hours).toString()

    }


}
