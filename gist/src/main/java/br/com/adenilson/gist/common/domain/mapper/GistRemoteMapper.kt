package br.com.adenilson.gist.common.domain.mapper

import br.com.adenilson.core.domain.Mapper
import br.com.adenilson.gist.common.domain.model.File
import br.com.adenilson.gist.common.domain.model.Gist
import br.com.adenilson.gist.common.domain.model.Owner
import br.com.adenilson.network.response.FileResponse
import br.com.adenilson.network.response.GistResponse
import br.com.adenilson.network.response.OwnerResponse
import javax.inject.Inject

interface GistRemoteMapper : Mapper<GistResponse, Gist>

class GistRemoteMapperImpl @Inject constructor() : GistRemoteMapper {

    override fun mapTo(params: GistResponse): Gist {
        return Gist(
            description = params.description,
            webId = params.id,
            htmlUrl = params.htmlUrl,
            owner = mapOwner(params.owner),
            files = mapFiles(params.files.values.toList())
        )
    }

    private fun mapFiles(filesModel: List<FileResponse>): List<File> {
        return filesModel.map { fileModel ->
            File(
                filename = fileModel.filename,
                type = fileModel.type,
                rawUrl = fileModel.rawUrl,
                size = fileModel.size,
                language = fileModel.language
            )
        }
    }

    private fun mapOwner(ownerModel: OwnerResponse): Owner {
        return Owner(
            avatarUrl = ownerModel.avatarUrl,
            name = ownerModel.login
        )
    }
}