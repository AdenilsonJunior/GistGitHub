package br.com.adenilson.infrastructure.mapper

import br.com.adenilson.core.domain.Mapper
import br.com.adenilson.data.model.FileModel
import br.com.adenilson.data.model.GistModel
import br.com.adenilson.data.model.OwnerModel
import br.com.adenilson.database.entity.FileEntity
import br.com.adenilson.database.entity.GistEntity
import br.com.adenilson.database.entity.OwnerEntity
import javax.inject.Inject

interface GistModelMapper : Mapper<GistEntity, GistModel>

class GistModelMapperImpl @Inject constructor() : GistModelMapper {
    override fun mapTo(params: GistEntity): GistModel {
        return GistModel(
            id = params.id,
            description = params.description,
            lastUpdate = params.lastUpdate,
            webId = params.webId,
            favorite = params.favorite,
            files = mapFilesModel(params.files),
            owner = mapOwnerModel(params.owner)
        )
    }

    private fun mapOwnerModel(owner: OwnerEntity): OwnerModel {
        return OwnerModel(
            id = owner.id,
            avatarUrl = owner.avatarUrl,
            name = owner.name
        )
    }

    private fun mapFilesModel(files: List<FileEntity>): List<FileModel> {
        return files.map { file ->
            FileModel(
                id = file.id,
                filename = file.filename,
                type = file.type,
                rawUrl = file.rawUrl,
                size = file.size,
                language = file.language
            )
        }
    }
}