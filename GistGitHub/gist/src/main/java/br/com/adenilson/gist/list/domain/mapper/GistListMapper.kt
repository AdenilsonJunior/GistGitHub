package br.com.adenilson.gist.list.domain.mapper

import br.com.adenilson.base.domain.Mapper
import br.com.adenilson.gist.list.domain.model.File
import br.com.adenilson.gist.list.domain.model.Gist
import br.com.adenilson.gist.list.domain.model.Owner
import br.com.adenilson.network.model.FileResponse
import br.com.adenilson.network.model.GistResponse
import br.com.adenilson.network.model.OwnerResponse
import javax.inject.Inject

interface GistListMapper : Mapper<List<GistResponse>, List<Gist>>

class GistListMapperImpl @Inject constructor() : GistListMapper {
    override fun mapToPresentation(params: List<GistResponse>): List<Gist> {
        return params.map { gistResponse ->
            Gist(
                webId = gistResponse.id,
                owner = mapOwner(gistResponse.owner),
                files = mapFiles(gistResponse.files)
            )
        }
    }

    private fun mapOwner(owner: OwnerResponse): Owner {
        return owner.let {
            Owner(
                avatarUrl = it.avatarUrl,
                name = it.login
            )
        }
    }

    private fun mapFiles(files: Map<String, FileResponse>): List<File> {
        return files.values.map { file ->
            File(
                filename = file.filename,
                rawUrl = file.rawUrl,
                type = file.type
            )
        }
    }
}