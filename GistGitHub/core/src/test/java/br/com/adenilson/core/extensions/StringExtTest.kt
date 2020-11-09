package br.com.adenilson.core.extensions

import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import java.text.ParseException
import java.util.Calendar

@RunWith(MockitoJUnitRunner::class)
class StringExtTest {

    @Test
    fun `should parse string (pattern server) to date`() {
        val expected = Calendar.getInstance().apply {
            set(Calendar.DAY_OF_MONTH, 8)
            set(Calendar.MONTH, 10)
            set(Calendar.YEAR, 2020)
            set(Calendar.HOUR_OF_DAY, 21)
            set(Calendar.MINUTE, 42)
            set(Calendar.SECOND, 36)
        }.time
        val date = "2020-11-08T21:42:36Z".parseToDate()
        assertEquals(expected.toString(), date.toString())
    }

    @Test(expected = ParseException::class)
    fun `should throws error trying parse a invalid pattern server`() {
        "2020-11-08".parseToDate()
    }
}