package br.com.adenilson.infrastructure.mapper

import br.com.adenilson.core.domain.Mapper
import br.com.adenilson.core.extensions.parseToDate
import br.com.adenilson.data.model.FileModel
import br.com.adenilson.data.model.GistModel
import br.com.adenilson.data.model.OwnerModel
import br.com.adenilson.network.model.FileResponse
import br.com.adenilson.network.model.GistResponse
import br.com.adenilson.network.model.OwnerResponse
import javax.inject.Inject

interface GistResponseMapper : Mapper<GistResponse, GistModel>

class GistResponseMapperImpl @Inject constructor() : GistResponseMapper {
    override fun mapTo(params: GistResponse): GistModel {
        return GistModel(
            webId = params.id,
            owner = mapOwner(params.owner),
            files = mapFiles(params.files),
            description = params.description,
            lastUpdate = params.lastUpdate.parseToDate()
        )
    }

    private fun mapOwner(owner: OwnerResponse): OwnerModel {
        return owner.let {
            OwnerModel(
                avatarUrl = it.avatarUrl,
                name = it.login
            )
        }
    }

    private fun mapFiles(files: Map<String, FileResponse>): List<FileModel> {
        return files.values.map { file ->
            FileModel(
                filename = file.filename,
                rawUrl = file.rawUrl,
                type = file.type,
                size = file.size,
                language = file.language
            )
        }
    }
}