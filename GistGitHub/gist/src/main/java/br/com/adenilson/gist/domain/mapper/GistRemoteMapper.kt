package br.com.adenilson.gist.domain.mapper

import br.com.adenilson.core.domain.Mapper
import br.com.adenilson.core.extensions.parseToDate
import br.com.adenilson.gist.presentation.model.File
import br.com.adenilson.gist.presentation.model.Gist
import br.com.adenilson.gist.presentation.model.Owner
import br.com.adenilson.network.model.FileResponse
import br.com.adenilson.network.model.GistResponse
import br.com.adenilson.network.model.OwnerResponse
import javax.inject.Inject

interface GistRemoteMapper : Mapper<GistResponse, Gist>

class GistRemoteMapperImpl @Inject constructor() : GistRemoteMapper {

    override fun mapTo(params: GistResponse): Gist {
        return Gist(
            description = params.description,
            lastUpdate = params.lastUpdate.parseToDate(),
            webId = params.id,
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