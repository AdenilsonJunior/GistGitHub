package br.com.adenilson.gist.details.presentation.adapter

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
    override val url: String,
    val language: String?,
    val size: Long
) : NavigableUrlItem(url)

data class HtmlUrlItem(
    override val url: String
) : NavigableUrlItem(url)

abstract class NavigableUrlItem(open val url: String) : GistDetailsItem()