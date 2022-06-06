package ru.magomedcoder.askue.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
fun getDateString(last_seen_at: String): String {
    val dateStr = last_seen_at.split(".")[0]
    val date: LocalDateTime = LocalDateTime.parse(
        dateStr,
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())
    )
    return date.hour.toString() + ":" + date.minute.toString() + " " + date.dayOfMonth.toString() + "." + date.monthValue.toString() + "." + date.year.toString()
}