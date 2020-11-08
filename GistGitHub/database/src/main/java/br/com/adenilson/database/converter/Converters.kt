package br.com.adenilson.database.converter

import androidx.room.TypeConverter
import br.com.adenilson.database.entity.FileEntity
import com.google.gson.Gson
import com.google.gson.JsonParser
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class Converters {

    companion object {
        private const val SERVER_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    }

    @TypeConverter
    fun fromString(dateString: String): Date? {
        return SimpleDateFormat(SERVER_PATTERN, Locale.getDefault()).parse(dateString)
    }

    @TypeConverter
    fun fromDate(date: Date): String {
        return SimpleDateFormat(SERVER_PATTERN, Locale.getDefault()).format(date)
    }

    @TypeConverter
    fun filesFromJson(filesString: String): List<FileEntity> {
        val listType = object : TypeToken<List<FileEntity>>() {}.type
        return Gson().fromJson(JsonParser.parseString(filesString), listType)
    }

    @TypeConverter
    fun jsonFromFiles(files: List<FileEntity>): String {
        return Gson().toJson(files)
    }
}