package br.com.adenilson.infrastructure.mapper

import br.com.adenilson.core.domain.Mapper
import br.com.adenilson.data.model.FileModel
import br.com.adenilson.data.model.GistModel
import br.com.adenilson.data.model.OwnerModel
import br.com.adenilson.database.entity.FileEntity
import br.com.adenilson.database.entity.GistEntity
import br.com.adenilson.database.entity.OwnerEntity
import javax.inject.Inject

interface GistEntityMapper : Mapper<GistModel, GistEntity>

class GistEntityMapperImpl @Inject constructor() : GistEntityMapper {

    override fun mapTo(params: GistModel): GistEntity {
        return GistEntity(
            id = params.id,
            webId = params.webId,
            lastUpdate = params.lastUpdate,
            description = params.description,
            files = mapFiles(params.files),
            owner = mapOwner(params.owner),
            favorite = params.favorite
        )
    }

    private fun mapFiles(files: List<FileModel>): List<FileEntity> {
        return files.map { file ->
            FileEntity(
                id = file.id,
                language = file.language,
                size = file.size,
                rawUrl = file.rawUrl,
                type = file.type,
                filename = file.filename
            )
        }
    }

    private fun mapOwner(owner: OwnerModel): OwnerEntity {
        return OwnerEntity(
            name = owner.name,
            id = owner.id,
            avatarUrl = owner.avatarUrl
        )
    }
}