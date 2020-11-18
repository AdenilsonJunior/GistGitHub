package br.com.adenilson.database.converter

import androidx.room.TypeConverter
import br.com.adenilson.database.entity.FileEntity
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory

class Converters {

    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()

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