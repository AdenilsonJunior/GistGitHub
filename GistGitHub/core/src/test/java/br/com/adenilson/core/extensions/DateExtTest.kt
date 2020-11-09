package br.com.adenilson.core.extensions

import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.text.ParseException
import java.util.Calendar
import java.util.Date

@RunWith(MockitoJUnitRunner::class)
class DateExtTest {

    @Test
    fun `should parse date to front pattern`() {
        val expected = "08/11/2020 às 21:42:36"
        val date = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 8)
            set(Calendar.MONTH, 10)
            set(Calendar.YEAR, 2020)
            set(Calendar.HOUR_OF_DAY, 21)
            set(Calendar.MINUTE, 42)
            set(Calendar.SECOND, 36)
        }.time.parseToString()

        assertEquals(expected, date)
    }
}