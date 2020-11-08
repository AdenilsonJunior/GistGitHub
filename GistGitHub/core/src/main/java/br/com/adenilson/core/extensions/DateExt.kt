package br.com.adenilson.core.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val FRONT_PATTERN = "dd/MM/yyyy à's' HH:mm:ss"

fun Date.parseToString(pattern: String = FRONT_PATTERN): String {
    return SimpleDateFormat(pattern, Locale.getDefault()).format(this)
}