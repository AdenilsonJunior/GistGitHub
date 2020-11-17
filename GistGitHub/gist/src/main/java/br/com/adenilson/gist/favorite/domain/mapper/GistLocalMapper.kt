package br.com.adenilson.gist.favorite.domain.mapper

import br.com.adenilson.core.domain.Mapper
import br.com.adenilson.database.entity.FileEntity
import br.com.adenilson.database.entity.GistEntity
import br.com.adenilson.database.entity.OwnerEntity
import br.com.adenilson.gist.commons.domain.model.File
import br.com.adenilson.gist.commons.domain.model.Gist
import br.com.adenilson.gist.commons.domain.model.Owner
import javax.inject.Inject

interface GistLocalMapper : Mapper<GistEntity, Gist>

class GistLocalMapperImpl @Inject constructor() : GistLocalMapper {
    override fun mapTo(params: GistEntity): Gist {
        return Gist(
            id = params.id,
            description = params.description,
            lastUpdate = params.lastUpdate,
            webId = params.webId,
            favorite = params.favorite,
            files = mapFilesModel(params.files),
            owner = mapOwnerModel(params.owner)
        )
    }

    private fun mapOwnerModel(owner: OwnerEntity): Owner {
        return Owner(
            id = owner.id,
            avatarUrl = owner.avatarUrl,
            name = owner.name
        )
    }

    private fun mapFilesModel(files: List<FileEntity>): List<File> {
        return files.map { file ->
            File(
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