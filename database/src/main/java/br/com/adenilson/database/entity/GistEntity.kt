package br.com.adenilson.database.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
@Entity(tableName = "gist", indices = [Index(value = ["web_id"], unique = true)])
data class GistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long?,
    @ColumnInfo(name = "web_id")
    val webId: String,
    @Embedded(prefix = "owner_")
    val owner: OwnerEntity,
    val files: List<FileEntity>,
    val description: String?,
    @ColumnInfo(name = "html_url")
    val htmlUrl: String,
    var favorite: Boolean
)

data class DeleteGist(
    @ColumnInfo(name = "web_id")
    val webId: String
)