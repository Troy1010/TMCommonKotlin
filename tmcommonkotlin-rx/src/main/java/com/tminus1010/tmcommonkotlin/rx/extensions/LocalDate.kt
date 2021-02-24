package com.tminus1010.tmcommonkotlin.rx.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.temporal.TemporalAdjusters

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.previous(dayOfWeek: DayOfWeek) =
    this.with(TemporalAdjusters.previous(dayOfWeek))

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.previousOrSame(dayOfWeek: DayOfWeek) =
    this.with(TemporalAdjusters.previousOrSame(dayOfWeek))

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.next(dayOfWeek: DayOfWeek) =
    this.with(TemporalAdjusters.next(dayOfWeek))

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.nextOrSame(dayOfWeek: DayOfWeek) =
    this.with(TemporalAdjusters.nextOrSame(dayOfWeek))

@RequiresApi(Build.VERSION_CODES.O)
fun LocalDate.toDisplayStr() =
    DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
        .format(this)