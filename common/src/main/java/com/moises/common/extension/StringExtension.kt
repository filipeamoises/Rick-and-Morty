package com.moises.rickymorty.util.extension

import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.*

fun String.toLocalDate(): Date {
    val ta: TemporalAccessor = DateTimeFormatter.ISO_INSTANT.parse(this)
    val i: Instant = Instant.from(ta)
    return Date.from(i)
}