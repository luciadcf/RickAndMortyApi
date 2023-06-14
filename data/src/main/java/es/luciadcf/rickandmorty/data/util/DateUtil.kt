package es.luciadcf.rickandmorty.data.util

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    private const val DEFAULT_FORMAT_DATE = "MM-dd-yyyy"

    fun getAgeFromDate(dateString: String, format: String = DEFAULT_FORMAT_DATE): Int {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        val date = sdf.parse(dateString)
        date?.let {
            val calendar = Calendar.getInstance().apply {
                time = date
            }
            return getAge(
                calendar[Calendar.YEAR],
                calendar[Calendar.MONTH],
                calendar[Calendar.DAY_OF_MONTH]
            )
        } ?: run {
            return -1
        }
    }

    private fun getAge(year: Int, month: Int, day: Int): Int {
        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()
        dob[year, month] = day
        var age = today[Calendar.YEAR] - dob[Calendar.YEAR]
        if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
            age--
        }
        return age
    }
}