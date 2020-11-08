package br.com.adenilson.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "file")
class FileEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    val filename: String,
    val type: String,
    @ColumnInfo(name = "raw_url")
    val rawUrl: String,
    val language: String?,
    val size: Long
)