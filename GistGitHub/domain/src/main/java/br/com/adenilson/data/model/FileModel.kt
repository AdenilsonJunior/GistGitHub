package br.com.adenilson.data.model

data class FileModel(
    val id: Long? = null,
    val filename: String,
    val type: String,
    val rawUrl: String,
    val language: String?,
    val size: Long
)