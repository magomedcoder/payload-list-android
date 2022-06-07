package ru.magomedcoder.askue.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
fun convertFormatDateFromIso(value: String?): String {
    if (value == null) {
        return ""
    }
    val actual = OffsetDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME)
    val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")
    return actual.format(formatter)
}