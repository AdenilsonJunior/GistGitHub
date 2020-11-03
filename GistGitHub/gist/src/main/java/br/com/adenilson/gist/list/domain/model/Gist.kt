package br.com.adenilson.gist.list.domain.model

data class Gist(
    var id: Long? = null,
    val webId: String,
    val owner: Owner,
    val files: List<File>
)