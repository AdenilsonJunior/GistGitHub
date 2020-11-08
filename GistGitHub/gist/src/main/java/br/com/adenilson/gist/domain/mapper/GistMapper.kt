package br.com.adenilson.gist.domain.mapper

import br.com.adenilson.core.domain.Mapper
import br.com.adenilson.data.model.FileModel
import br.com.adenilson.data.model.GistModel
import br.com.adenilson.data.model.OwnerModel
import br.com.adenilson.gist.presentation.model.File
import br.com.adenilson.gist.presentation.model.Gist
import br.com.adenilson.gist.presentation.model.Owner
import javax.inject.Inject

interface GistMapper : Mapper<GistModel, Gist>

class GistMapperImpl @Inject constructor() : GistMapper {

    override fun mapTo(params: GistModel): Gist {
        return Gist(
            id = params.id,
            description = params.description,
            lastUpdate = params.lastUpdate,
            favorite = params.favorite,
            webId = params.webId,
            owner = mapOwner(params.owner),
            files = mapFiles(params.files)
        )
    }

    private fun mapFiles(filesModel: List<FileModel>): List<File> {
        return filesModel.map { fileModel ->
            File(
                id = fileModel.id,
                filename = fileModel.filename,
                type = fileModel.type,
                rawUrl = fileModel.rawUrl,
                size = fileModel.size,
                language = fileModel.language
            )
        }
    }

    private fun mapOwner(ownerModel: OwnerModel): Owner {
        return Owner(
            id = ownerModel.id,
            avatarUrl = ownerModel.avatarUrl,
            name = ownerModel.name
        )
    }
}