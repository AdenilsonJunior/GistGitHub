package br.com.adenilson.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "gist")
class GistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    @ColumnInfo(name = "web_id")
    val webId: String,
    @Embedded(prefix = "owner_")
    val owner: OwnerEntity,
    val files: List<FileEntity>,
    val description: String?,
    @ColumnInfo(name = "last_update")
    val lastUpdate: Date?,
    val favorite: Boolean
)