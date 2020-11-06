package br.com.adenilson.gist.presentation.details.adapter

import java.util.Date

abstract class GistDetailsItem

data class HeaderItem(
    val avatar: String,
    val userName: String,
    val gistType: String,
    val description: String?
) : GistDetailsItem()

data class FilesHeaderItem(
    val count: Int
) : GistDetailsItem()

data class FileItem(
    val filename: String,
    val type: String,
    val rawUrl: String,
    val language: String?,
    val size: Long
):  GistDetailsItem()

data class UpdateDateItem(
    val date: Date
): GistDetailsItem()