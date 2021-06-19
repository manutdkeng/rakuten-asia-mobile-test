package com.zack.android.test.rakuten

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class DateFormatTest {

    @Test
    fun testDateFormat() {
        val dateString = "2012-08-08T21:49:39.837528+00:00"
        val dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
        val sdf = SimpleDateFormat(dateFormat, Locale.ENGLISH)
        val printSdf = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ENGLISH)

        try {
            val date = sdf.parse(dateString)
            val printDate: String = printSdf.format(date)
            assertThat(printDate).isEqualTo("2012-08-08 21:49")
        } catch (e: Exception) {
        }
    }
}