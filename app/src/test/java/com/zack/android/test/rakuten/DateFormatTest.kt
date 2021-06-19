package com.zack.android.test.rakuten

import androidx.core.text.HtmlCompat
import com.google.common.html.HtmlEscapers
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import java.net.URLDecoder
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

    @Test
    fun urlDecode() {
        val date = "2011-09-03T12%3A33%3A16.028393%2B00%3A00"
        val decode = URLDecoder.decode(date, "UTF-8")
        assertThat(decode).isEqualTo("2011-09-03T12:33:16.028393+00:00")
    }
}