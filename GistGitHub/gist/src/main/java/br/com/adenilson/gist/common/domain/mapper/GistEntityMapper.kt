package br.com.adenilson.gist.common.domain.mapper

import br.com.adenilson.core.domain.Mapper
import br.com.adenilson.database.entity.FileEntity
import br.com.adenilson.database.entity.GistEntity
import br.com.adenilson.database.entity.OwnerEntity
import br.com.adenilson.gist.common.domain.model.File
import br.com.adenilson.gist.common.domain.model.Gist
import br.com.adenilson.gist.common.domain.model.Owner
import javax.inject.Inject

interface GistEntityMapper : Mapper<Gist, GistEntity>

class GistEntityMapperImpl @Inject constructor() : GistEntityMapper {

    override fun mapTo(params: Gist): GistEntity {
        return GistEntity(
            id = params.id,
            webId = params.webId,
            htmlUrl = params.htmlUrl,
            description = params.description,
            files = mapFiles(params.files),
            owner = mapOwner(params.owner),
            favorite = params.favorite
        )
    }

    private fun mapFiles(files: List<File>): List<FileEntity> {
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

    private fun mapOwner(owner: Owner): OwnerEntity {
        return OwnerEntity(
            name = owner.name,
            id = owner.id,
            avatarUrl = owner.avatarUrl
        )
    }
}