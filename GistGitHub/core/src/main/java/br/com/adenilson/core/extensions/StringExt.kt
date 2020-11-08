package br.com.adenilson.core.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val SERVER_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"

fun String.parseToDate(pattern: String = SERVER_PATTERN): Date? {
    return SimpleDateFormat(pattern, Locale.getDefault()).parse(this)
}