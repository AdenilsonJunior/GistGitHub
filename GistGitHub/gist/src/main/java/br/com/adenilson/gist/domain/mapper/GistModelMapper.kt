package br.com.adenilson.gist.domain.mapper

import br.com.adenilson.core.domain.Mapper
import br.com.adenilson.data.model.FileModel
import br.com.adenilson.data.model.GistModel
import br.com.adenilson.data.model.OwnerModel
import br.com.adenilson.gist.presentation.model.File
import br.com.adenilson.gist.presentation.model.Gist
import br.com.adenilson.gist.presentation.model.Owner
import javax.inject.Inject

interface GistModelMapper : Mapper<Gist, GistModel>

class GistModelMapperImpl @Inject constructor() : GistModelMapper {

    override fun mapTo(params: Gist): GistModel {
        return GistModel(
            id = params.id,
            webId = params.webId,
            favorite = params.favorite,
            lastUpdate = params.lastUpdate,
            description = params.description,
            owner = mapOwnerModel(params.owner),
            files = mapFilesModel(params.files)
        )
    }

    private fun mapFilesModel(files: List<File>): List<FileModel> {
        return files.map { file ->
            FileModel(
                id = file.id,
                language = file.language,
                size = file.size,
                rawUrl = file.rawUrl,
                type = file.type,
                filename = file.filename
            )
        }
    }

    private fun mapOwnerModel(owner: Owner): OwnerModel {
        return OwnerModel(
            id = owner.id,
            name = owner.name,
            avatarUrl = owner.avatarUrl
        )
    }
}