package br.com.adenilson.database.converter

import androidx.room.TypeConverter
import br.com.adenilson.database.entity.FileEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class Converters {

    companion object {
        private const val SERVER_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    }

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

    @TypeConverter
    fun fromString(dateString: String): Date? {
        return SimpleDateFormat(SERVER_PATTERN, Locale.getDefault()).parse(dateString)
    }

    @TypeConverter
    fun fromDate(date: Date): String {
        return SimpleDateFormat(SERVER_PATTERN, Locale.getDefault()).format(date)
    }

    @TypeConverter
    fun filesFromJson(filesString: String): List<FileEntity>? {
        val type = Types.newParameterizedType(List::class.java, FileEntity::class.java)
        return moshi.adapter<List<FileEntity>>(type).fromJson(filesString)
    }

    @TypeConverter
    fun jsonFromFiles(files: List<FileEntity>): String {
        val type = Types.newParameterizedType(List::class.java, FileEntity::class.java)
        return moshi.adapter<List<FileEntity>>(type).toJson(files)
    }
}