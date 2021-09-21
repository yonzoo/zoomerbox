package com.zoomerbox.data.provider.local.room.converter

import androidx.room.TypeConverter
import java.util.stream.Collectors

class Converters {

    @TypeConverter
    fun fromListOfStrings(strings: List<String>): String {
        return strings.stream().collect(Collectors.joining(","));
    }

    @TypeConverter
    fun toListOfStrings(string: String): List<String> {
        return string.split(",").toList()
    }

    @TypeConverter
    fun fromListOfLongs(strings: List<Long>): String {
        return strings.stream().map { l -> l.toString() }.collect(Collectors.joining(","))
    }

    @TypeConverter
    fun toListOfLongs(string: String): List<Long> {
        return string.split(",").toList().map { s -> s.toLong() }
    }
}