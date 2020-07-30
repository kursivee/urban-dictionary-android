package com.kursivee.urbandictionary.common.util.date.ext

import android.annotation.SuppressLint
import com.kursivee.urbandictionary.common.util.date.DateFormats
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun String.parseDate(): String {
    var format = SimpleDateFormat(DateFormats.URBAN_DICTIONARY_DATE_FORMAT)
    val newDate = format.parse(this) ?: return ""
    format = SimpleDateFormat(DateFormats.OUTPUT_DATE_FORMAT)
    return format.format(newDate)
}
