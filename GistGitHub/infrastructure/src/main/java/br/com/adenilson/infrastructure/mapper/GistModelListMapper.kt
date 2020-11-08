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

interface GistModelListMapper : Mapper<List<GistResponse>, List<GistModel>>

class GistModelListMapperImpl @Inject constructor() : GistModelListMapper {
    override fun mapTo(params: List<GistResponse>): List<GistModel> {
        return params.map { gistResponse ->
            GistModel(
                webId = gistResponse.id,
                owner = mapOwner(gistResponse.owner),
                files = mapFiles(gistResponse.files),
                description = gistResponse.description,
                lastUpdate = gistResponse.lastUpdate.parseToDate()
            )
        }
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